package kr.or.kosa.cmsplusmain.domain.base.repository;

import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBilling.*;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.*;
import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.*;
import static kr.or.kosa.cmsplusmain.domain.payment.entity.QPayment.*;
import static org.springframework.util.StringUtils.*;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageDto;
import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEntity;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentType;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public abstract class BaseRepository<T extends BaseEntity, ID extends Serializable> {

	protected final EntityManager em;
	protected final JPAQueryFactory jpaQueryFactory;

	/*
	 * 새로생성 혹은 수정완료시 (비영속 상태 객체 수정 완료 후 호출)
	 * */
	@Transactional
	public T save(T entity) {
		return em.merge(entity);
	}

	/*
	 * 정렬 조건 생성
	 *
	 * 기본 조건: 생성일 내림차순
	 * */
	protected OrderSpecifier<?> orderMethod(PageDto.Req pageable) {
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

	protected BooleanExpression memberNameContains(String memberName) {
		return hasText(memberName) ? member.name.containsIgnoreCase(memberName) : null;
	}

	protected BooleanExpression memberPhoneContains(String memberPhone) {
		return hasText(memberPhone) ? member.phone.containsIgnoreCase(memberPhone) : null;
	}

	protected BooleanExpression paymentTypeEq(PaymentType paymentType) {
		return (paymentType != null) ? payment.paymentType.eq(paymentType) : null;
	}

	protected BooleanExpression contractDayEq(Integer contractDay) {
		return (contractDay != null) ? contract.contractDay.eq(contractDay) : null;
	}

	protected BooleanExpression billingStatusEq(BillingStatus billingStatus) {
		return (billingStatus != null) ? billing.status.eq(billingStatus) : null;
	}

	protected BooleanExpression contractStatusEq(ContractStatus contractStatus) {
		return (contractStatus != null) ? contract.status.eq(contractStatus) : null;
	}

	protected BooleanExpression consentStatusEq(ConsentStatus consentStatus) {
		return (consentStatus != null) ? payment.consentStatus.eq(consentStatus) : null;
	}
}
