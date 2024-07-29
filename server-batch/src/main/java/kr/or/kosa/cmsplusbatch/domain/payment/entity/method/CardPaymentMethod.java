package kr.or.kosa.cmsplusbatch.domain.payment.entity.method;

import java.time.LocalDate;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Comment("결제수단 - 카드")
@Entity
@DiscriminatorValue(PaymentMethod.Const.CARD)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardPaymentMethod extends PaymentMethodInfo {

	@Comment("카드 번호")
	@Column(name = "card_info_number", nullable = false)
	@NotNull
	private String cardNumber;

	@Comment("카드 유효기간 월")
	@Column(name = "card_info_validity_month", nullable = false)
	@NotNull
	private Integer cardMonth;

	@Comment("카드 유효기간 년")
	@Column(name = "card_info_validity_year", nullable = false)
	@NotNull
	private Integer cardYear;

	@Comment("카드 소유주명")
	@Column(name = "card_info_owner", nullable = false)
	@NotNull
	private String cardOwner;

	@Comment("카드 소유주 생년월일")
	@Column(name = "card_info_owner_birth", nullable = false)
	@NotNull
	private LocalDate cardOwnerBirth;
}

