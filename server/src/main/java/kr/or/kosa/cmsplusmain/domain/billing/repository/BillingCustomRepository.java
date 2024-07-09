package kr.or.kosa.cmsplusmain.domain.billing.repository;

import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBilling.*;
import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingProduct.*;
import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingStandard.*;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.*;
import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.*;
import static kr.or.kosa.cmsplusmain.domain.payment.entity.QPayment.*;
import static kr.or.kosa.cmsplusmain.domain.product.entity.QProduct.*;
import static kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseCustomRepository;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItem;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingSearch;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;

@Repository
public class BillingCustomRepository extends BaseCustomRepository<Billing> {

	public BillingCustomRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
		super(em, jpaQueryFactory);
	}

	/*
	 * 청구목록 조회
	 * */
	public List<BillingListItem> findBillingListWithCondition(String vendorUsername, BillingSearch search, SortPageDto.Req pageable) {

		List<Tuple> res = jpaQueryFactory
			.select(
				billing.id,
				member.name, member.phone,
				billingProduct.id, billingProduct.billingStandard.id,
				billingProduct.product, billingProduct.price, billingProduct.quantity,
				billingProduct.price.multiply(billingProduct.quantity).sum().as("billingPrice"),
				billing.billingStatus,
				payment.paymentType,
				billing.billingDate
			)
			.from(billing)

			.join(billing.billingStandard, billingStandard)
			.join(billingStandard.billingProducts, billingProduct)
			.join(billingProduct.product, product)
			.join(billingStandard.contract, contract)
			.join(contract.vendor, vendor)
			.join(contract.member, member)
			.join(contract.payment, payment)

			.where(
				billingNotDel(),
				billingProductNotDel(),

				vendorUsernameEq(vendorUsername),

				memberNameContains(search.getMemberName()),
				memberPhoneContains(search.getMemberPhone()),
				billingPriceLoe(search.getBillingPrice()),
				billingStatusEq(search.getBillingStatus()),
				paymentTypeEq(search.getPaymentType()),
				billingDateEq(search.getBillingDate())
			)

			.groupBy(
				billing.id, billing.billingDate, billing.billingStatus, member.name, member.phone,
				payment.paymentType, billingStandard.id, billingStandard.type,
				billingProduct.id, billingProduct.product, billingProduct.price, billingProduct.quantity
			)

			.orderBy(orderMethod(pageable))

			.offset(pageable.getPage())
			.limit(pageable.getSize())
			.fetch();


		return null;
	}

	/*
	 * 계약 상세 - 청구목록
	 *
	 * 최신순 정렬
	 * */
	public List<Billing> findBillingsByContractId(Long contractId, SortPageDto.Req pageable) {
		return jpaQueryFactory
			.selectFrom(billing)
			.join(billing.billingStandard, billingStandard).fetchJoin()
			.join(billingStandard.contract, contract).fetchJoin()
			.join(contract.member, member).fetchJoin()
			.join(contract.payment, payment).fetchJoin()
			.where(
				billingNotDel(),
				contract.id.eq(contractId)
			)
			.orderBy(billing.createdDateTime.desc())
			.offset(pageable.getPage())
			.limit(pageable.getSize())
			.fetch();
	}


}
