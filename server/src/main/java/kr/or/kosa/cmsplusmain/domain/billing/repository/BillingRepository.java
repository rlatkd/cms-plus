package kr.or.kosa.cmsplusmain.domain.billing.repository;

import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBilling.*;
import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingProduct.*;
import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingStandard.*;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.*;
import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.*;
import static kr.or.kosa.cmsplusmain.domain.payment.entity.QPayment.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageDto;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseRepository;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItem;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingProduct;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStandard;

@Repository
public class BillingRepository extends BaseRepository<Billing, Long> {

	public BillingRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
		super(em, jpaQueryFactory);
	}

	/*
	 * 계약 상세 - 청구목록
	 *
	 * 최신순 정렬
	 * */
	public List<BillingListItem> findBillingsByContractId(Long contractId, PageDto.Req pageable) {
		List<Long> billingIds = jpaQueryFactory
			.select(billing.id)
			.from(billing)
			.join(billing.billingStandard, billingStandard)
			.where(
				billingStandard.contract.id.eq(contractId),
				billing.deleted.eq(false))
			.offset(pageable.getPage())
			.limit(pageable.getSize())
			.fetch();

		List<Billing> billings = jpaQueryFactory
			.selectFrom(billing)
			.join(billing.billingStandard, billingStandard).fetchJoin()
			.join(billingStandard.member, member).fetchJoin()
			.join(billingStandard.contract, contract).fetchJoin()
			.join(contract.payment, payment).fetchJoin()
			.where(billing.id.in(billingIds))
			.orderBy(billing.createdDateTime.desc())
			.fetch();

		Map<Long, List<BillingProduct>> idToBillingProducts = jpaQueryFactory
			.selectFrom(billingProduct)
			.where(
				billingProduct.billing.id.in(billingIds),
				billingProduct.deleted.eq(false))
			.fetch()
			.stream()
			.collect(Collectors.groupingBy(bp -> bp.getBilling().getId()));

		return billings.stream()
			.map(mBilling -> {
				BillingStandard bs = mBilling.getBillingStandard();
				return BillingListItem.builder()
					.billingId(mBilling.getId())
					.memberName(bs.getMember().getName())
					.billingDate(mBilling.getBillingDate())
					.billingProducts(idToBillingProducts.get(mBilling.getId()))
					.billingStatus(mBilling.getStatus())
					.paymentType(bs.getContract().getPayment().getPaymentType())
					.contractDate(bs.getContractDate())
					.build();
			})
			.toList();
	}
}
