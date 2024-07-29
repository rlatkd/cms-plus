package kr.or.kosa.cmsplusbatch.domain.payment.entity.type;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.DiscriminatorValue;
import kr.or.kosa.cmsplusbatch.domain.payment.entity.method.PaymentMethod;
import kr.or.kosa.cmsplusbatch.domain.payment.exception.InvalidBuyerMethodException;
import lombok.Builder;
import org.hibernate.annotations.Comment;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Comment("결제방식-납부자결제")
@Entity
@Getter
@DiscriminatorValue(PaymentType.Const.BUYER)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BuyerPaymentType extends PaymentTypeInfo {

	// TODO transaction 전파 안됨??
	@Comment("납부자결제 - 설정된 가능 결제수단")
	@ElementCollection(targetClass = PaymentMethod.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "buyer_payment_method")
	@Enumerated(EnumType.STRING)
	@Column(name = "buyer_payment_method")
	private Set<PaymentMethod> availableMethods = new HashSet<>();

	@Builder
	public BuyerPaymentType(Set<PaymentMethod> availableMethods) {
		if (availableMethods == null || availableMethods.isEmpty()) {
			throw new InvalidBuyerMethodException("납부자 결제수단은 최소 한 개 이상이 필요합니다.");
		}
		setAvailableMethods(availableMethods);
	}

	/*
	 * 납부자결제 수단은 카드와 계좌만 가능하다.
	 * */
	public void setAvailableMethods(Set<PaymentMethod> availableMethods) {
		List<PaymentMethod> paymentMethods = PaymentType.getBuyerPaymentMethods();
		if (availableMethods.stream().anyMatch(method -> !paymentMethods.contains(method))) {
			throw new InvalidBuyerMethodException("납부자결제 가능 결제수단이 아닙니다.");
		}
		this.availableMethods = availableMethods;
	}
}
