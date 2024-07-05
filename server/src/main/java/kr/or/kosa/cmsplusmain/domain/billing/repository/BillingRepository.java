package kr.or.kosa.cmsplusmain.domain.billing.repository;

import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBilling.*;
import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingProduct.*;
import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingStandard.*;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.*;
import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.*;
import static kr.or.kosa.cmsplusmain.domain.payment.entity.QPayment.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageDto;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseRepository;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItem;
import kr.or.kosa.cmsplusmain.domain.billing.dto.QBillingListItem;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;

@Repository
public class BillingRepository extends BaseRepository<Billing, Long> {

	public BillingRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
		super(em, jpaQueryFactory);
	}

	/*
	* 계약 상세 - 청구목록
	* */
	public List<BillingListItem> findBillingsByContractId(Long contractId, PageDto.Req pageable) {
		return jpaQueryFactory
			.from(billing)
			.join(billing.billingStandard, billingStandard)
			.join(billingStandard.member, member)
			.join(billingStandard.contract, contract)
			.join(contract.payment, payment)
			.leftJoin(billing.billingProducts, billingProduct).on(billingProduct.deleted.eq(false))
			.where(
				billing.deleted.eq(false),
				contract.id.eq(contractId))
			.orderBy(billing.createdDateTime.desc())
			.offset(pageable.getPage())
			.limit(pageable.getSize())
			.transform(GroupBy.groupBy(billing.id).list(Projections.constructor(BillingListItem.class,
				billing.id,
				member.name,
				billing.billingDate,
				GroupBy.list(billingProduct),
				billing.status,
				payment.paymentType,
				billingStandard.contractDate)));

	}
}
