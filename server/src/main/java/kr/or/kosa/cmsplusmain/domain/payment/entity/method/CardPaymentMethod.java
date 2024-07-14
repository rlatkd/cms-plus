package kr.or.kosa.cmsplusmain.domain.payment.entity.method;

import java.time.LocalDate;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.base.validator.PersonName;
import kr.or.kosa.cmsplusmain.domain.payment.validator.CardNumber;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Comment("결제수단 - 카드")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardPaymentMethod extends PaymentMethodInfo {

	@Comment("카드 번호")
	@Column(name = "card_info_number", nullable = false)
	@CardNumber
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
	@PersonName
	@NotNull
	private String cardOwner;

	@Comment("카드 소유주 생년월일")
	@Column(name = "card_info_owner_birth", nullable = false)
	@NotNull
	private LocalDate cardOwnerBirth;


}

