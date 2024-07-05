package kr.or.kosa.cmsplusmain.domain.contract.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageDto;
import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEntity;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseRepository;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractListItem;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractProductDto;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;
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
	*
	*  */
	public List<ContractListItem> findContractsWithCondition(String vendorUsername, ContractSearch search, PageDto.Req pageable) {
		return jpaQueryFactory
			.from(contract)
			.join(contract.vendor, vendor)
			.join(contract.member, member)
			.leftJoin(contract.contractProducts, contractProduct).on(contractProduct.deleted.eq(false))
			.join(contract.payment, payment)
			.where(
				member.deleted.eq(false),
				contract.deleted.eq(false),
				vendor.username.eq(vendorUsername),
				memberNameContains(search.getMemberName()),
				memberPhoneContains(search.getMemberPhone()),
				contractDayEq(search.getContractDay()),
				productNameContains(search.getProductName()),
				contractPriceLoe(search.getContractPrice()),
				consentStatusEq(search.getConsentStatus())
			)
			.orderBy(orderMethod(pageable))
			.offset(pageable.getPage())
			.limit(pageable.getSize())
			.transform(GroupBy.groupBy(contract.id).list(Projections.constructor(ContractListItem.class,
				contract.id,
				member.name,
				member.phone,
				contract.contractDay,
				GroupBy.list(contractProduct),
				contract.contractPrice,
				contract.status,
				payment.consentStatus)));
	}

	/*
	* 상세 계약 정보 (청구 목록 제외)
	*
	* 동일 트랜잭션 내에서 수정 금지
	* */
	@Transactional(readOnly = true)
	public Contract findContractDetailById(Long contractId) {
		return jpaQueryFactory
			.selectFrom(contract)
			.join(contract.member, member).fetchJoin()
			.leftJoin(contract.contractProducts, contractProduct).fetchJoin()
			.join(contract.payment, payment).fetchJoin()
			.where(
				contract.id.eq(contractId),
				contract.deleted.eq(false),
				contractProduct.deleted.eq(false)
			)
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
