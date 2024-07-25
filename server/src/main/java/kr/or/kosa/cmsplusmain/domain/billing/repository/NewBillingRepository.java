package kr.or.kosa.cmsplusmain.domain.billing.repository;

import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBilling.*;
import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingProduct.*;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.*;
import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.*;
import static kr.or.kosa.cmsplusmain.domain.payment.entity.QPayment.*;
import static kr.or.kosa.cmsplusmain.util.QueryExpressions.*;

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

@Repository
public class NewBillingRepository extends NewBaseRepository<Billing, Long> {

	public Billing findById(Long id) {
		return select(billing, billing)
			.from(billing)
			.where(billing.id.eq(id))
			.fetchOne();
	}

	/**
	 * 청구목록 검색 |
	 * 기본정렬: 생성시간 내림차순
	 * */
	public List<BillingListItemRes> searchBillings(Long vendorId, BillingSearchReq search, PageReq pageReq) {
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
			))
			.groupBy(member.name, member.phone, billing.billingStatus, payment.paymentType, billing.billingDate);

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
	 * 조인이 다 수 걸리므로,
	 * 리턴값이 BillingListItemRes 인 경우만 사용한다.
	 * */
	private JPAQuery<?> searchQuery(Long vendorId, BillingSearchReq search) {
		return from(billing, billing)
			.join(billing.contract, contract)
			.join(contract.member, member)
			.join(contract.payment, payment)
			.leftJoin(billing.billingProducts, billingProduct).on(isNotDeleted(billingProduct))
			.where(
				contractVendorIdEq(vendorId),							// 고객 일치
				memberNameContains(search.getMemberName()),				// 회원명 포함
				memberPhoneContains(search.getMemberPhone()),			// 휴대전화 포함
				billingStatusEq(search.getBillingStatus()),				// 청구상태 일치
				paymentTypeEq(search.getPaymentType()),					// 결제방식 일치
				billingDateEq(search.getBillingDate()),					// 결제일 일치
				productNameContainsInBilling(search.getProductName()),	// 상품명 포함
				contractIdEq(search.getContractId())					// 계약 ID 일치 (계약 상세 청구목록)
			)
			.groupBy(billing.id)
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
}
