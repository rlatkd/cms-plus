package kr.or.kosa.cmsplusmain.domain.contract.repository;

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
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageDto;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseRepository;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractListItem;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractSearch;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ContractRepository extends BaseRepository<Contract, Long> {

	public ContractRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
		super(em, jpaQueryFactory);
	}

	/*
	 * 계약 목록 조회
	 *
	 * TODO: 쿼리 횟수 감소시키기 (inheritance 쿼리 한 번더 나가는거 방지)
	 * TODO: 검색 최적화를 위한 캐싱하기
	 *  */
	public List<ContractListItem> findContractListWithCondition(String vendorUsername, ContractSearch search,
		PageDto.Req pageable) {

		// 회원명, 회원 휴대전화, 약정일, 계약상태, 동의상태
		List<Long> contractIds = jpaQueryFactory
			.select(contract.id)
			.from(contract)
			.join(contract.vendor, vendor)
			.join(contract.member, member)
			.join(contract.payment, payment)
			.where(
				// 삭제여부
				contract.deleted.eq(false),
				member.deleted.eq(false),

				contract.vendor.username.eq(vendorUsername),    // 해당 고객의 계약

				// 검색조건
				memberNameContains(search.getMemberName()),        // 회원명
				memberPhoneContains(search.getMemberPhone()),    // 휴대전화
				contractDayEq(search.getContractDay()),            // 약정일
				contractStatusEq(search.getContractStatus()),    // 계약상태
				consentStatusEq(search.getConsentStatus())        // 동의상태
			).fetch();

		// 상품 목록
		Map<Long, List<ContractProduct>> contractIdToProducts = jpaQueryFactory
			.select(contractProduct)
			.from(contractProduct)
			.where(
				contractProduct.contract.id.in(contractIds),
				contractProduct.deleted.eq(false)
			)
			.fetch()
			.stream()
			.collect(Collectors.groupingBy(cp -> cp.getContract().getId()));

		// 검색 상품명이 포함된 상품이 하나라도 존재하는 계약
		// 계약 금액이 검색 금액이하인 계약
		contractIdToProducts.entrySet().removeIf(entry -> {
			List<ContractProduct> products = entry.getValue();

			// 검색 상품명 포함
			boolean containsProductName = products.stream()
				.map(ContractProduct::getName)
				.anyMatch(name -> search.getProductName() == null || name.contains(search.getProductName()));

			// 계약 금액 이하
			boolean loeContractPrice = products.stream()
				.mapToLong(ContractProduct::getTotalPrice)
				.sum() <= (search.getContractPrice() == null ? Long.MAX_VALUE : search.getContractPrice());

			return !(containsProductName && loeContractPrice);
		});

		// 검색 결과 계약 ID 목록
		// 페이징 적용을 위해 분리
		Set<Long> searchedContractIds = contractIdToProducts.keySet();
		List<Tuple> results = jpaQueryFactory
			.select(
				contract.id,
				member.name,
				member.phone,
				contract.contractDay,
				contract.status,
				contract.payment)
			.from(contract)
			.join(contract.member, member)
			.join(contract.payment, payment)
			.where(contract.id.in(searchedContractIds))
			.orderBy(orderMethod(pageable))
			.offset(pageable.getPage())
			.limit(pageable.getSize())
			.fetch();

		// DTO 변환
		List<ContractListItem> items = new ArrayList<>();
		for (Tuple result : results) {
			Long contractId = result.get(contract.id);

			List<ContractProduct> products = contractIdToProducts.get(contractId);
			Long contractPrice = products.stream()
				.mapToLong(ContractProduct::getTotalPrice)
				.sum();

			Payment mPayment = result.get(contract.payment);
			ConsentStatus consentStatus = (mPayment != null) ?
				mPayment.getConsentStatus() : null;

			ContractListItem item = ContractListItem.builder()
				.contractId(contractId)
				.memberName(result.get(member.name))
				.memberPhone(result.get(member.phone))
				.contractDay(result.get(contract.contractDay))
				.contractProducts(products)
				.contractPrice(contractPrice)
				.contractStatus(result.get(contract.status))
				.consentStatus(consentStatus)
				.build();

			items.add(item);
		}

		return items;
	}

	/*
	 * 계약 상세 조회
	 *
	 * 동일 트랜잭션 내에서 수정금지
	 * */
	@Transactional(readOnly = true)
	public Contract findContractDetailById(Long id) {
		return jpaQueryFactory
			.selectFrom(contract)
			.leftJoin(contract.contractProducts, contractProduct).fetchJoin()
			.join(contract.member, member).fetchJoin()
			.join(contract.payment, payment).fetchJoin()
			.where(
				contract.deleted.eq(false),
				contract.id.eq(id),
				contractProduct.deleted.eq(false))
			.fetchOne();
	}

	/*
	 * 계약 정보 수정
	 * */
	@Transactional
	public void updateContract(Long contractId, String contractName, List<ContractProduct> contractProducts) {

		// 기존 계약 상품 삭제
		jpaQueryFactory
			.update(contractProduct)
			.where(contractProduct.contract.id.eq(contractId))
			.set(contractProduct.deleted, true)
			.execute();

		// 새로운 계약 상품 추가
		contractProducts.forEach(em::persist);

		// 계약 이름 변경
		jpaQueryFactory
			.update(contract)
			.where(contract.id.eq(contractId))
			.set(contract.name, contractName)
			.execute();
	}

	/*
	 * 존재 여부
	 * */
	public boolean isExistById(Long contractId) {
		Integer fetchOne = jpaQueryFactory
			.selectOne()
			.from(contract)
			.where(contract.id.eq(contractId))
			.fetchFirst();
		return fetchOne != null;
	}

	/*
	 * 정렬 조건 생성
	 *
	 * 기본 조건: 생성일 내림차순
	 * */
	private OrderSpecifier<?> orderMethod(PageDto.Req pageable) {
		if (pageable.getOrderBy() == null) {
			return new OrderSpecifier<>(Order.DESC, contract.createdDateTime);
		}

		Order order = pageable.isAsc() ? Order.ASC : Order.DESC;

		return switch (pageable.getOrderBy()) {
			case "memberName" -> new OrderSpecifier<>(order, member.name);
			case "contractDay" -> new OrderSpecifier<>(order, contract.contractDay);
			case "contractPrice" -> new OrderSpecifier<>(order, contract.contractPrice);
			default -> new OrderSpecifier<>(Order.DESC, contract.createdDateTime);
		};
	}

	private BooleanExpression memberNameContains(String memberName) {
		return hasText(memberName) ? member.name.containsIgnoreCase(memberName) : null;
	}

	private BooleanExpression memberPhoneContains(String memberPhone) {
		return hasText(memberPhone) ? member.phone.containsIgnoreCase(memberPhone) : null;
	}

	private BooleanExpression contractDayEq(Integer contractDay) {
		return (contractDay != null) ? contract.contractDay.eq(contractDay) : null;
	}

	private BooleanExpression contractStatusEq(ContractStatus contractStatus) {
		return (contractStatus != null) ? contract.status.eq(contractStatus) : null;
	}

	private BooleanExpression consentStatusEq(ConsentStatus consentStatus) {
		return (consentStatus != null) ? payment.consentStatus.eq(consentStatus) : null;
	}
}
