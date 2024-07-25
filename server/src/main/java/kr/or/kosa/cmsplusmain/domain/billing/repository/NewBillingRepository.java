package kr.or.kosa.cmsplusmain.domain.billing.repository;

import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBilling.*;
import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingProduct.*;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.*;
import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.*;
import static kr.or.kosa.cmsplusmain.domain.payment.entity.QPayment.*;
import static org.springframework.util.StringUtils.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;

import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.repository.NewBaseRepository;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItemRes;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingSearchReq;
import kr.or.kosa.cmsplusmain.domain.billing.dto.QBillingListItemRes;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;

@Repository
public class NewBillingRepository extends NewBaseRepository<Billing, Long> {

	public Billing findById(Long id) {
		return select(billing, billing)
			.from(billing)
			.where(billing.id.eq(id))
			.fetchOne();
	}

	/**
	 * 청구목록 조회 |
	 * 기본정렬: 생성시간 내림차순
	 * */
	public List<BillingListItemRes> findBillingListWithCondition(Long vendorId, BillingSearchReq search, PageReq pageReq) {
		JPAQuery<BillingListItemRes> query = searchQuery(vendorId, search)
			.select(new QBillingListItemRes(
				billing.id,
				member.name,
				member.phone,
				billingProduct.price.longValue().multiply(billingProduct.quantity).sum(),
				billing.billingStatus,
				payment.paymentType,
				billing.billingDate,
				getFirstProductName(search.getProductName()),
				billingProduct.countDistinct()
			));

		return applyPagingAndSort(query, pageReq).fetch();
	}

	/**
	 * 고객의 전체 계약 수 (검색 조건 반영된 계약 수)
	 * */
	public long countBillingListWithCondition(Long vendorId, BillingSearchReq search) {
		// fetchCount : 서브쿼리를 생성해서 count 해준다.
		// 기본적으로 count 쿼리보다 성능이 좋지 않지만
		// groupBY 절에서 어차피 서브쿼리를 통해 카운트 필요
		return searchQuery(vendorId, search).fetchCount();
	}

	/**
	 * 검색 조건 쿼리 생성
	 * */
	private JPAQuery<?> searchQuery(Long vendorId, BillingSearchReq search) {
		return from(billing, billing)
			.join(billing.contract, contract)
			.join(contract.member, member)
			.join(contract.payment, payment)
			.leftJoin(billing.billingProducts, billingProduct).on(isNotDeleted(billingProduct))
			.where(
				// billingNotDel(),
				contractVendorIdEq(vendorId),
				memberNameContains(search.getMemberName()),
				memberPhoneContains(search.getMemberPhone()),
				billingStatusEq(search.getBillingStatus()),
				paymentTypeEq(search.getPaymentType()),
				billingDateEq(search.getBillingDate()),
				productNameContainsInBilling(search.getProductName()),
				contractIdEq(search.getContractId())
			)
			.groupBy(billing.id, member.name, member.phone, billing.billingStatus, payment.paymentType, billing.billingDate)
			.having(billingPriceLoe(search.getBillingPrice()));
	}

	/**
	 * 고객이 해당 청구를 가지고 있는지 확인
	 * */
	public boolean existByVendorId(Long billingId, Long vendorId) {
		Integer res = selectOne(billing)
			.from(billing)
			.join(billing.contract, contract)
			.where(
				// billingNotDel(),
				contract.vendor.id.eq(vendorId),
				billing.id.eq(billingId)
			)
			.fetchOne();

		return res != null;
	}

	/**
	 * 청구 상세 조회를 위한 계약, 회원, 결제 fetch join
	 * */
	public Billing findBillingWithContract(Long billingId) {
		return select(billing, billing)
			.from(billing)
			.join(billing.contract, contract).fetchJoin()
			.join(contract.member, member).fetchJoin()
			.join(contract.payment, payment).fetchJoin()
			.where(
				// billingNotDel(),
				billing.id.eq(billingId)
			)
			.fetchOne();
	}

	/*********** 조건 ************/

	private BooleanExpression paymentTypeEq(PaymentType paymentType) {
		return (paymentType != null) ? payment.paymentType.eq(paymentType) : null;
	}
	private BooleanExpression billingNotDel() {
		return billing.deleted.isFalse();
	}
	private BooleanExpression contractVendorIdEq(Long vendorId) {
		return (vendorId != null) ? contract.vendor.id.eq(vendorId) : null;
	}
	private BooleanExpression billingStatusEq(BillingStatus billingStatus) {
		return (billingStatus != null) ? billing.billingStatus.eq(billingStatus) : null;
	}
	private BooleanExpression billingDateEq(LocalDate billingDate) {
		return (billingDate != null) ? billing.billingDate.eq(billingDate) : null;
	}
	private StringExpression getFirstProductName(String productName) {
		if (StringUtils.hasText(productName)) {
			return Expressions.stringTemplate(
				"COALESCE({0}, {1})",
				JPAExpressions
					.select(Expressions.stringTemplate("ANY_VALUE({0})", billingProduct.name))
					.from(billingProduct)
					.where(billingProduct.billing.eq(billing),
						billingProduct.name.contains(productName))
					.groupBy(billingProduct.billing.id),
				billingProduct.name.min()
			);
		} else {
			return billingProduct.name.min();
		}
	}

	private BooleanExpression productNameContainsInBilling(String productName) {
		if (!StringUtils.hasText(productName)) {
			return null;
		}
		return billing.id.in(
			JPAExpressions
				.select(billingProduct.billing.id)
				.from(billingProduct)
				.where(billingProduct.name.contains(productName))
		);
	}
	private BooleanExpression billingPriceLoe(Long billingPrice) {
		return billingPrice != null ? billingProduct.price.multiply(billingProduct.quantity).sum().loe(billingPrice) : null;
	}
	private BooleanExpression contractIdEq(Long contractId) {
		return contractId != null ? contract.id.eq(contractId) : null;
	}

	protected BooleanExpression memberNameContains(String memberName) {
		return hasText(memberName) ? member.name.containsIgnoreCase(memberName) : null;
	}

	protected BooleanExpression memberPhoneContains(String memberPhone) {
		return hasText(memberPhone) ? member.phone.containsIgnoreCase(memberPhone) : null;
	}
}
