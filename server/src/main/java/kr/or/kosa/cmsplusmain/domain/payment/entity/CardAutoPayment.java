package kr.or.kosa.cmsplusmain.domain.payment.entity;

import java.time.LocalDate;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Comment("결제방식 - 자동결제 - 카드")
@Entity
@Table(name = "card_auto_payment")
@DiscriminatorValue(PaymentType.Values.AUTO + "-" + PaymentMethod.Values.CARD)
@Getter
//TODO
public class CardAutoPayment extends AutoPayment {

	@Column(name = "card_auto_card_number")
	private String cardNumber;

	@Column(name = "card_auto_card_owner")
	private String cardOwner;

	@Column(name = "card_auto_owner_birth")
	private LocalDate cardOwnerBirth;
}
