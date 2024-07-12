package kr.or.kosa.cmsplusmain.domain.billing.repository;



import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBilling.*;
import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingProduct.*;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.*;
import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.*;
import static kr.or.kosa.cmsplusmain.domain.payment.entity.QPayment.*;
import static kr.or.kosa.cmsplusmain.domain.product.entity.QProduct.*;
import static kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseCustomRepository;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingSearchReq;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BillingCustomRepository extends BaseCustomRepository<Billing> {

	public BillingCustomRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
		super(em, jpaQueryFactory);
	}

	/*
	 * 청구목록 조회
	 *
	 * 총 2번의 쿼리
	 * 1. billing
	 * 2. billingProduct <- batchsize 100
	 * */
	public List<Billing> findBillingListWithCondition(Long vendorId, BillingSearchReq search, PageReq pageReq) {
		return jpaQueryFactory
			.selectFrom(billing)

			.join(billing.contract, contract).fetchJoin()
			.join(contract.member, member).fetchJoin()
			.join(contract.payment, payment).fetchJoin()

			.where(
				billingNotDel(),                                	// 청구 삭제 여부
				contract.vendor.id.eq(vendorId),					// 고객 일치
				memberNameContains(search.getMemberName()),        	// 회원 이름 포함
				memberPhoneContains(search.getMemberPhone()),    	// 회원 휴대전화 포함
				billingStatusEq(search.getBillingStatus()),        	// 청구상태 일치
				paymentTypeEq(search.getPaymentType()),            	// 결제방식 일치
				billingDateEq(search.getBillingDate())            	// 결제일 일치
			)

			.groupBy(billing.id)
			.having(
				productNameContainsInGroup(search.getProductName()),    // 청구상품 이름 포함
				billingPriceLoeInGroup(search.getBillingPrice())        // 청구금액 이하
			)

			.orderBy(
				buildOrderSpecifier(pageReq)
					.orElse(billing.createdDateTime.desc())				// 기본 정렬 = 생성시간 내림차순
			)

			.offset(pageReq.getPage())
			.limit(pageReq.getSize())
			.fetch();
	}

	/*
	 * 고객의 전체 계약 수 (검색 조건 반영된 계약 수)
	 * */
	public int countAllBillings(Long vendorId, BillingSearchReq search) {
		Long count = jpaQueryFactory
			.select(billing.id.countDistinct())
			.from(billing)

			.join(billing.contract, contract)
			.join(contract.member, member)
			.join(contract.payment, payment)

			.where(
				billingNotDel(),                                	// 청구 삭제 여부
				contract.vendor.id.eq(vendorId),					// 고객 일치
				memberNameContains(search.getMemberName()),        	// 회원 이름 포함
				memberPhoneContains(search.getMemberPhone()),    	// 회원 휴대전화 포함
				billingStatusEq(search.getBillingStatus()),        	// 청구상태 일치
				paymentTypeEq(search.getPaymentType()),            	// 결제방식 일치
				billingDateEq(search.getBillingDate())            	// 결제일 일치
			)

			.groupBy(billing.id)
			.having(
				productNameContainsInGroup(search.getProductName()),    // 청구상품 이름 포함
				billingPriceLoeInGroup(search.getBillingPrice())        // 청구금액 이하
			)

			.fetchOne();

		return (count != null) ? count.intValue() : 0;
	}

	/*
	 * 고객의 계약 존재 여부
	 * */
	public boolean isExistBillingByUsername(Long billingId, Long vendorId) {
		Integer res = jpaQueryFactory
			.selectOne()
			.from(billing)
			.join(billing.contract, contract)
			.where(
				contract.vendor.id.eq(vendorId),
				billing.id.eq(billingId),
				billingNotDel()
			)
			.fetchOne();

		return res != null;
	}

	/*
	 * 청구 상세
	 * */
	public Billing findBillingDetail(Long billingId) {
		return jpaQueryFactory
			.selectFrom(billing)
			.join(billing.contract, contract).fetchJoin()
			.join(contract.member, member).fetchJoin()
			.join(contract.payment, payment).fetchJoin()
			.where(
				billingNotDel(),
				billing.id.eq(billingId)
			)
			.fetchOne();
	}

	/*
	 * 계약 상세 - 청구목록
	 *
	 * 최신순 정렬
	 * */
	public List<Billing> findBillingsByContractId(Long contractId, PageReq pageable) {
		return jpaQueryFactory
			.selectFrom(billing)
			.join(billing.contract, contract)
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

	/*
	 * 회원 상세 - 기본정보(청구수)
	 * */
	public int findBillingStandardByMemberId(Long vendorId, Long memberId){
		Long res = jpaQueryFactory
			.select(member.id.countDistinct())
			.from(billing)
			.join(billing.contract, contract)
			.where(
					contract.vendor.id.eq(vendorId),
					contract.member.id.eq(memberId),
					contractNotDel()
			)
			.fetchOne();

		return (res == null) ? 0 : res.intValue();
	}

	/*
	 * 계약의 청구 전체 개수
	 * */
	public int countAllBillingsByContract(Long contractId) {
		Long res = jpaQueryFactory
			.select(billing.id.countDistinct())
			.from(billing)
			.where(
				billingNotDel(),
				billing.contract.id.eq(contractId)
			)
			.fetchOne();

		return (res != null) ? res.intValue() : 0;
	}

	/*
	 회원 상세 - 기본정보(청구금액)
	  */
	public Long findBillingProductByMemberId(Long vendorId, Long memberId){
		return jpaQueryFactory
				.select(billingProduct.price.longValue().multiply(billingProduct.quantity).sum())
				.from(billingProduct)
				.join(billingProduct.billing, billing)
				.join(billing.contract, contract)
				.where(
						contract.vendor.id.eq(vendorId),
						member.id.eq(memberId),
						contractNotDel(),
						billingProductNotDel()
				)
				.fetchOne();
	}

	private BooleanExpression paymentTypeEq(PaymentType paymentType) {
		return (paymentType != null) ? payment.paymentType.eq(paymentType) : null;
	}
}
