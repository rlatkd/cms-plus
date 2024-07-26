package kr.or.kosa.cmsplusmain.domain.contract.repository;

import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.*;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContractProduct.*;
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
import kr.or.kosa.cmsplusmain.domain.base.repository.V2BaseRepository;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractSearchReq;
import kr.or.kosa.cmsplusmain.domain.contract.dto.QV2ContractListItemRes;
import kr.or.kosa.cmsplusmain.domain.contract.dto.V2ContractListItemRes;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class V2ContractRepository extends V2BaseRepository<Contract, Long> {

	/**
	 * 계약목록 검색 |
	 * */
	public List<V2ContractListItemRes> searchContracts(Long vendorId, ContractSearchReq search, PageReq pageReq) {
		JPAQuery<V2ContractListItemRes> query = searchQuery(vendorId, search)
			.select(new QV2ContractListItemRes(
				contract.id, member.name, member.phone, contract.contractDay,
				contractProduct.price.longValue().multiply(contractProduct.quantity).sum(),
				payment.paymentType, contract.contractStatus,
				getFirstProductName(search.getProductName()),
				contractProduct.countDistinct()
			))
			.groupBy(member.name, member.phone, contract.contractDay, payment.paymentType, contract.contractStatus);

		return applyPaging(query, pageReq).fetch();
	}

	/**
	 * 고객의 전체 청구 수 (검색 조건 반영된 청구 수)
	 * */
	public long countSearchedContracts(Long vendorId, ContractSearchReq search) {
		// fetchCount : 서브쿼리를 생성해서 count 해준다.
		// 기본적으로 count 쿼리보다 성능이 좋지 않지만
		// groupBY 절에서 어차피 서브쿼리를 통해 카운트 필요
		return searchQuery(vendorId, search).fetchCount();
	}

	private JPAQuery<?> searchQuery(Long vendorId, ContractSearchReq search) {
		return from(contract)
			.join(contract.member, member)
			.join(contract.payment, payment)
			.leftJoin(contract.contractProducts, contractProduct).on(isNotDeleted(contractProduct))
			.where(
				contractVendorIdEq(vendorId),
				contractStatusEq(search.getContractStatus()),
				memberNameContains(search.getMemberName()),
				memberPhoneContains(search.getMemberPhone()),
				paymentTypeEq(search.getPaymentType()),
				paymentMethodEq(search.getPaymentMethod()),
				productNameContainsInContract(search.getProductName()),
				memberIdEq(search.getMemberId())
			)
			.groupBy(contract.id)
			.having(contractPriceLoe(search.getContractPrice()));
	}

	/*********** 조건 ************/
	private StringExpression getFirstProductName(String productName) {
		if (StringUtils.hasText(productName)) {
			return Expressions.stringTemplate(
				"COALESCE({0}, {1})",
				JPAExpressions
					.select(Expressions.stringTemplate("ANY_VALUE({0})", contractProduct.name))
					.from(contractProduct)
					.where(contractProduct.contract.eq(contract),
						contractProduct.name.contains(productName))
					.groupBy(contractProduct.contract.id),
				contractProduct.name.min()
			);
		} else {
			return contractProduct.name.min();
		}
	}

	private BooleanExpression contractPriceLoe(Long contractPrice) {
		return contractPrice != null ? contractProduct.price.multiply(contractProduct.quantity).sum().loe(contractPrice) : null;
	}

	private BooleanExpression productNameContainsInContract(String productName) {
		if (!StringUtils.hasText(productName)) {
			return null;
		}
		return contract.id.in(
			JPAExpressions
				.select(contractProduct.contract.id)
				.from(contractProduct)
				.where(contractProduct.name.contains(productName))
		);
	}
}
