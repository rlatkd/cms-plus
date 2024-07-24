package kr.or.kosa.cmsplusmain.domain.base.repository;


import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBilling.billing;
import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingProduct.billingProduct;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.contract;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContractProduct.contractProduct;
import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.member;
import static kr.or.kosa.cmsplusmain.domain.product.entity.QProduct.product;
import static org.springframework.util.StringUtils.*;

import java.time.LocalDate;
import java.util.Optional;
import java.time.LocalDateTime;

import com.querydsl.core.types.Path;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEntity;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public abstract class BaseCustomRepository<T extends BaseEntity> {

	protected final EntityManager em;
	protected final JPAQueryFactory jpaQueryFactory;

	/*
	 * 정렬 조건 생성
	 *
	 * TODO 각자의 레포지토리에서 하는게 좋을듯?
	 * */
	protected Optional<OrderSpecifier<?>> buildOrderSpecifier(PageReq pageReq) {
		if (pageReq == null || !StringUtils.hasText(pageReq.getOrderBy())) {
			return Optional.empty();
		}

		EntityPathBase<?> entity = null;
		String fieldName = null;
		Order order = pageReq.isAsc() ? Order.ASC : Order.DESC;

		switch (pageReq.getOrderBy()) {
			case "memberName" -> {
				fieldName = "name";
				entity = member;
			}

			case "billingPrice" -> {
				NumberExpression<Long> expression =
					billingProduct.price.longValue().multiply(billingProduct.quantity).sum();
				return Optional.ofNullable(pageReq.isAsc() ? expression.asc() : expression.desc());
			}
			case "billingDate" -> {
				fieldName = "billingDate";
				entity = billing;
			}

			case "contractDay" -> {
				fieldName = "contractDay";
				entity = contract;
			}
			case "contractPrice" -> {
				NumberExpression<Long> expression =
					contractProduct.price.longValue().multiply(contractProduct.quantity).sum();
				return Optional.ofNullable(pageReq.isAsc() ? expression.asc() : expression.desc());
			}
		}

		return Optional.ofNullable(getSortColumn(entity, fieldName, order));
	}

	private OrderSpecifier<?> getSortColumn(EntityPathBase<?> entity, String fieldName, Order order) {
		if (entity == null || fieldName == null) {
			return null;
		}

		ComparablePath<?> path = new PathBuilder<>(entity.getType(), entity.getMetadata().getName())
			.getComparable(fieldName, Comparable.class);
		return (order.equals(Order.ASC)) ? path.asc() : path.desc();
	}

	// TODO 각자 레포지토리로 이동
	protected BooleanExpression productVendorIdEq(Long vendorId) {
		if (vendorId == null) {
			return null;
		}
		return product.vendor.id.eq(vendorId);
	}

	protected BooleanExpression memberNotDel() {
		return member.deleted.isFalse();
	}


	protected BooleanExpression billingProductNotDel() {
		return billingProduct.deleted.isFalse();
	}

	protected BooleanExpression contractNotDel() {
		return contract.deleted.isFalse();
	}

	protected BooleanExpression contractProductNotDel() {
		return contractProduct.deleted.isFalse();
	}

	protected BooleanExpression productNotDel() {
		return product.deleted.isFalse();
	}

	protected BooleanExpression memberNameContains(String memberName) {
		return hasText(memberName) ? member.name.containsIgnoreCase(memberName) : null;
	}

	protected BooleanExpression memberPhoneContains(String memberPhone) {
		return hasText(memberPhone) ? member.phone.containsIgnoreCase(memberPhone) : null;
	}

	protected BooleanExpression contractPriceLoeInGroup(Long contractPrice) {
		if (contractPrice == null)
			return null;
		return contractProduct.price.multiply(contractProduct.quantity).sum().loe(contractPrice);
	}

	// protected BooleanExpression paymentTypeEq(PaymentType paymentType) {
	// 	return (paymentType != null) ? payment.paymentType.eq(paymentType) : null;
	// }

	protected BooleanExpression contractDayEq(Integer contractDay) {
		return (contractDay != null) ? contract.contractDay.eq(contractDay) : null;
	}

	// protected BooleanExpression consentStatusEq(ConsentStatus consentStatus) {
	// 	return (consentStatus != null) ? payment.consentStatus.eq(consentStatus) : null;
	// }

	protected  BooleanExpression productMemoContains(String productMemo) {
		return hasText(productMemo) ? product.memo.containsIgnoreCase(productMemo) : null;
	}

	protected BooleanExpression productCreatedDateEq(LocalDate productCreatedDate) {
		return dateEq(product.createdDateTime, productCreatedDate);
	}

	protected BooleanExpression productPriceLoe(Integer productPrice) {
		if (productPrice == null)
			return null;
		return product.price.loe(productPrice);
	}



	/**
	 * Date형식(yyyy-MM-dd)로 들어온 DTO를 DB에 있는 DateTime(yyyy-MM-dd hh:mm:ss)와 비교하는 공통 메서드
	 * productCreatedDateEq 참고해서 쓰시오
	 * */
	protected BooleanExpression dateEq(Path<LocalDateTime> dateTimePath, LocalDate localDate) {
		if (localDate == null) {
			return null;
		}
		return Expressions.booleanTemplate(
				"DATE({0}) = {1}",
				dateTimePath, localDate
		);
	}

}
