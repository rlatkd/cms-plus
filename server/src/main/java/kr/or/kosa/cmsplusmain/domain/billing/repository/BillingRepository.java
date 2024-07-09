package kr.or.kosa.cmsplusmain.domain.billing.repository;

import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBilling.*;
import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingProduct.*;
import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingStandard.*;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.*;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContractProduct.*;
import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.*;
import static kr.or.kosa.cmsplusmain.domain.payment.entity.QPayment.*;
import static kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor.*;
import static org.springframework.util.StringUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageDto;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseRepository;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItem;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingSearch;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingProduct;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStandard;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractListItem;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;
import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentType;

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
					.memberPhone(bs.getMember().getPhone())
					.billingProducts(idToBillingProducts.get(mBilling.getId()))
					.billingStatus(mBilling.getStatus())
					.paymentType(bs.getContract().getPayment().getPaymentType())
					.contractDate(bs.getContractDate())
					.build();
			})
			.toList();
	}

	/*
	* 청구목록 조회
	* */
	public List<BillingListItem> findBillingListWithCondition(String vendorUsername, BillingSearch search, PageDto.Req pageable) {

		// 1번째 쿼리
		// 고객의 청구기준
		// 조건 검색: 회원명, 회원 휴대전화, 결제방식, 결제일
		List<Long> billingStandardIds = jpaQueryFactory
			.select(billingStandard.id)
			.from(billingStandard)
			.join(billingStandard.contract, contract)
			.join(contract.vendor, vendor)
			.join(contract.member, member)
			.join(contract.payment, payment)
			.where(
				// 삭제여부
				billingStandard.deleted.eq(false),
				member.deleted.eq(false),

				vendor.username.eq(vendorUsername),

				memberNameContains(search.getMemberName()),
				memberPhoneContains(search.getMemberPhone()),
				paymentTypeEq(search.getPaymentType()),
				contractDayEq(search.getContractDay())
			).fetch();

		// 2번째 쿼리
		// 찾은 청구기준의 청구 목록
		// 조건 검색: 청구 상태
		List<Long> billingIds = jpaQueryFactory
			.select(billing.id)
			.from(billing)
			.where(
				billing.deleted.eq(false),
				billing.billingStandard.id.in(billingStandardIds),
				billingStatusEq(search.getBillingStatus()))
			.fetch();

		// 3번째 쿼리
		// 찾은 청구 목록의 청구 상품 목록
		Map<Long, List<BillingProduct>> billingIdToProducts = jpaQueryFactory
			.select(billingProduct)
			.from(billingProduct)
			.where(
				billing.deleted.eq(false),
				billingProduct.billing.id.in(billingIds)
			)
			.fetch()
			.stream()
			.collect(Collectors.groupingBy(bp -> bp.getBilling().getId()));

		// 검색 상품명이 포함된 상품이 하나라도 존재하는 청구
		// 청구 금액이 검색 금액이하인 계약
		billingIdToProducts.entrySet().removeIf(entry -> {
			List<BillingProduct> products = entry.getValue();

			// 검색 상품명 포함
			boolean containsProductName = products.stream()
				.map(BillingProduct::getProductName)
				.anyMatch(name -> search.getProductName() == null || name.contains(search.getProductName()));

			// 계약 금액 이하
			boolean loeBillingPrice = products.stream()
				.mapToLong(BillingProduct::getTotalPrice)
				.sum() <= (search.getBillingPrice() == null ? Long.MAX_VALUE : search.getBillingPrice());

			return !(containsProductName && loeBillingPrice);
		});

		// 4번째 쿼리 + (5번째 쿼리: inheritance 한 번 더 나감)
		// 결과 목록 쿼리
		Set<Long> searchedBillingIds = billingIdToProducts.keySet();
		List<Tuple> results = jpaQueryFactory
			.select(
				billing.id,
				member.name,
				member.phone,
				billing.status,
				payment.paymentType,
				contract.contractDay)
			.from(billing)
			.join(billing.billingStandard, billingStandard)
			.join(billingStandard.member, member)
			.join(billingStandard.contract, contract)
			.join(contract.payment, payment)
			.where(billing.id.in(searchedBillingIds))
			.orderBy(orderMethod(pageable))
			.offset(pageable.getPage())
			.limit(pageable.getSize())
			.fetch();

		// DTO 변환
		List<BillingListItem> items = new ArrayList<>();
		for (Tuple result : results) {
			Long billingId = result.get(billing.id);

			List<BillingProduct> products = billingIdToProducts.get(billingId);
			Long billingPrice = products.stream()
				.mapToLong(BillingProduct::getTotalPrice)
				.sum();

			Payment mPayment = result.get(contract.payment);
			PaymentType paymentType = (mPayment != null) ?
				mPayment.getPaymentType() : null;


			BillingListItem item = BillingListItem.builder()
				.billingId(billingId)
				.memberName(result.get(member.name))
				.memberPhone(result.get(member.phone))
				.billingProducts(products)
				.billingPrice(billingPrice)
				.billingStatus(result.get(billing.status))
				.paymentType(paymentType)
				.contractDate(result.get(billingStandard.contractDate))
				.build();

			items.add(item);
		}

		return items;
	}


}
