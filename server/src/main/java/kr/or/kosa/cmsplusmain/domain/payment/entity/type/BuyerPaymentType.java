package kr.or.kosa.cmsplusmain.domain.payment.entity.type;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Builder;
import org.hibernate.annotations.Comment;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Comment("결제방식-납부자결제")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BuyerPaymentType extends PaymentTypeInfo {

	@Comment("납부자결제 - 설정된 가능 결제수단")
	@ElementCollection(targetClass = PaymentMethod.class, fetch = FetchType.LAZY)
	@CollectionTable(name = "buyer_payment_method")
	@Enumerated(EnumType.STRING)
	@Column(name = "buyer_payment_method")
	private Set<PaymentMethod> availableMethods = new HashSet<>();


	//TODO
	// 코드 점검 필요

	@Builder
	public BuyerPaymentType(Set<PaymentMethod> availableMethods) {
		setAvailableMethods(availableMethods);
	}

	/*
	 * 납부자결제 수단은 카드와 계좌만 가능하다.
	 * */
	public void setAvailableMethods(Set<PaymentMethod> availableMethods) {
		List<PaymentMethod> paymentMethods = PaymentType.getBuyerPaymentMethods();
		if (availableMethods.stream().anyMatch(method -> !paymentMethods.contains(method))) {
			throw new IllegalArgumentException("납부자결제 가능 결제수단이 아닙니다.");
		}
		this.availableMethods = availableMethods;
	}
}
