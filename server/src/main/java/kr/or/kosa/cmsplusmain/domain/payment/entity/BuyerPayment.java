package kr.or.kosa.cmsplusmain.domain.payment.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Comment;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import lombok.Getter;

@Comment("결제방식 - 납부자결제")
@Entity
@DiscriminatorValue(PaymentType.Values.BUYER)
@Getter
public class BuyerPayment extends Payment {

	@ElementCollection(targetClass = PaymentMethod.class, fetch = FetchType.LAZY)
	@CollectionTable(name = "buyer_payment_method")
	@Enumerated(EnumType.STRING)
	@Column(name = "buyer_payment_method")
	private Set<PaymentMethod> availableMethods = new HashSet<>();

	@Override
	public PaymentMethod getPaymentMethod() {
		return null;
	}

	//TODO 추가 삭제
	// 납부자 결제 수단만 추가 삭제 가능
}
