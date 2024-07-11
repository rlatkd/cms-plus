package kr.or.kosa.cmsplusmain.domain.base.repository;

import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBilling.*;
import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingProduct.*;
import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingStandard.*;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.*;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContractProduct.*;
import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.*;
import static kr.or.kosa.cmsplusmain.domain.payment.entity.QPayment.*;
import static kr.or.kosa.cmsplusmain.domain.product.entity.QProduct.*;
import static kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor.*;
import static org.springframework.util.StringUtils.*;

import java.time.LocalDate;
import java.util.Optional;

import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.product.entity.QProduct;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEntity;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentType;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public abstract class BaseCustomRepository<T extends BaseEntity> {

	protected final EntityManager em;
	protected final JPAQueryFactory jpaQueryFactory;

	/*
	 * 정렬 조건 생성
	 * */
	protected Optional<OrderSpecifier<?>> buildOrderSpecifier(SortPageDto.Req pageable) {
		if (pageable == null || !StringUtils.hasText(pageable.getOrderBy())) {
			return Optional.empty();
		}

		EntityPathBase<?> entity = null;
		String fieldName = null;
		Order order = pageable.isAsc() ? Order.ASC : Order.DESC;

		switch (pageable.getOrderBy()) {
			case "memberName" -> {
				fieldName = "name";
				entity = member;
			}

			case "billingPrice" -> {
				NumberExpression<Long> expression =
					billingProduct.price.longValue().multiply(billingProduct.quantity).sum();
				return Optional.ofNullable(pageable.isAsc() ? expression.asc() : expression.desc());
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
				return Optional.ofNullable(pageable.isAsc() ? expression.asc() : expression.desc());
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

	protected BooleanExpression vendorUsernameEq(String vendorUsername) {
		return hasText(vendorUsername) ? vendor.username.eq(vendorUsername) : null;
	}

	protected BooleanExpression billingNotDel() {
		return billing.deleted.isFalse();

	}protected BooleanExpression memberNotDel() {
		return member.deleted.isFalse();
	}

	protected BooleanExpression billingStandardNotDel() {
		return billingStandard.deleted.isFalse();
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

	protected BooleanExpression productNameContains(String productName) {
		return hasText(productName) ? product.name.containsIgnoreCase(productName) : null;
	}

	protected BooleanExpression productNameContainsInGroup(String productName) {
		if (productName == null)
			return null;
		return Expressions.booleanTemplate(
			"MAX(CASE WHEN {0} LIKE {1} THEN 1 ELSE 0 END) = 1",
			QProduct.product.name, "%" + productName + "%"
		);
	}

	protected BooleanExpression billingPriceLoeInGroup(Long billingPrice) {
		if (billingPrice == null)
			return null;
		return billingProduct.price.multiply(billingProduct.quantity).sum().loe(billingPrice);
	}

	protected BooleanExpression contractPriceLoeInGroup(Long contractPrice) {
		if (contractPrice == null)
			return null;
		return contractProduct.price.multiply(contractProduct.quantity).sum().loe(contractPrice);
	}

	protected BooleanExpression paymentTypeEq(PaymentType paymentType) {
		return (paymentType != null) ? payment.paymentType.eq(paymentType) : null;
	}

	protected BooleanExpression contractDayEq(Integer contractDay) {
		return (contractDay != null) ? contract.contractDay.eq(contractDay) : null;
	}

	protected BooleanExpression billingStatusEq(BillingStatus billingStatus) {
		return (billingStatus != null) ? billing.billingStatus.eq(billingStatus) : null;
	}

	protected BooleanExpression billingDateEq(LocalDate billingDate) {
		return (billingDate != null) ? billing.billingDate.eq(billingDate) : null;
	}

	protected BooleanExpression contractStatusEq(ContractStatus contractStatus) {
		return (contractStatus != null) ? contract.status.eq(contractStatus) : null;
	}

	protected BooleanExpression consentStatusEq(ConsentStatus consentStatus) {
		return (consentStatus != null) ? payment.consentStatus.eq(consentStatus) : null;
	}
}
