package kr.or.kosa.cmsplusmain.domain.contract.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageDto;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseRepository;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractListItem;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractProductDto;
import kr.or.kosa.cmsplusmain.domain.contract.dto.QContractListItem;
import kr.or.kosa.cmsplusmain.domain.contract.dto.QContractProductDto;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractSearch;
import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;

import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.contract;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContractProduct.contractProduct;
import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.*;
import static kr.or.kosa.cmsplusmain.domain.payment.entity.QPayment.*;
import static kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor.*;
import static org.springframework.util.StringUtils.*;

@Repository
public class ContractRepository extends BaseRepository<Contract, Long> {

	public ContractRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
		super(em, jpaQueryFactory);
	}

	/*
	* 계약 목록 조회
	*  */
	public List<ContractListItem> findContractsWithCondition(String vendorUsername, ContractSearch search, PageDto.Req pageable) {
		return jpaQueryFactory
			.select(new QContractListItem(
				contract.id,
				member.name, member.phone,
				contract.contractDay,
				contract.contractProducts,
				contract.contractPrice,
				contract.status,
				payment.consentStatus
			))
			.from(contract)

			// 조인. 삭제 여부 판단 필수
			.join(contract.vendor, vendor).on(vendor.username.eq(vendorUsername), vendor.deleted.eq(false))
			.join(contract.member, member).on(member.deleted.eq(false))
			.join(contract.contractProducts, contractProduct).on(contractProduct.deleted.eq(false))
			.join(contract.payment, payment).on(payment.deleted.eq(false))

			// 검색 조건
			.where(
				contract.deleted.eq(false),				// 삭제여부
				memberNameContains(search.getMemberName()),		// 회원 이름 포함
				memberPhoneContains(search.getMemberPhone()),	// 회원 휴대전화 포함
				contractDayEq(search.getContractDay()),			// 약정일 일치
				productNameContains(search.getProductName()),	// 상품 이름 포함
				contractPriceLoe(search.getContractPrice()),	// 계약금액 이하
				consentStatusEq(search.getConsentStatus())		// 동의상태
			)

			// 정렬
			.orderBy(orderMethod(pageable))

			.offset(pageable.getPage())
			.limit(pageable.getSize())
			.fetch();
	}

	/*
		상세 계약 정보 - 계약정보 - 계약 상품 목록
	*/
	public List<ContractProductDto> findContractProducts(Long contractId) {
		return jpaQueryFactory
			.select(new QContractProductDto(
				contractProduct.id,
				contractProduct.name,
				contractProduct.price,
				contractProduct.quantity))
			.from(contractProduct)
			.where(
				contractProduct.deleted.eq(false),
				contractProduct.contract.id.eq(contractId))
			.fetch();
	}

	/*
	* 상세 계약 정보 - 청구정보 - 회원 청구정보
	*
	* 항상 readonly transaction 안에서만 써야한다.
	* */
	public Contract findContractById(Long contractId) {
		return jpaQueryFactory
			.selectFrom(contract)
			.join(contract.member, member).fetchJoin()
			.join(contract.contractProducts, contractProduct).fetchJoin()
			.join(contract.payment, payment).fetchJoin()
			.where(
				contract.deleted.eq(false),
				contract.id.eq(contractId),
				member.deleted.eq(false),
				contractProduct.deleted.eq(false),
				payment.deleted.eq(false)
			)
			.fetchOne();
	}

	/*
	* 정렬 조건 생성
	*
	* 기본 조건: 생성일 내림차순
	* */
	private OrderSpecifier<?> orderMethod(PageDto.Req pageable){
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

	private BooleanExpression productNameContains(String productName) {
		return hasText(productName) ? contractProduct.name.containsIgnoreCase(productName) : null;
	}

	private BooleanExpression contractPriceLoe(Long contractPrice) {
		return (contractPrice != null) ? contract.contractPrice.loe(contractPrice) : null;
	}

	private BooleanExpression consentStatusEq(ConsentStatus consentStatus) {
		return (consentStatus != null) ? payment.consentStatus.eq(consentStatus) : null;
	}
}
