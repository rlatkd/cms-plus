package kr.or.kosa.cmsplusmain.domain.payment.dto.method;

import java.time.LocalDate;

import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CardMethodRes extends PaymentMethodInfoRes {
	private final String cardNumber;
	private final String cardOwner;
	private final LocalDate cardOwnerBirth;

	@Builder
	public CardMethodRes(String cardNumber, String cardOwner, LocalDate cardOwnerBirth) {
		super(PaymentMethod.CARD);
		this.cardNumber = cardNumber;
		this.cardOwner = cardOwner;
		this.cardOwnerBirth = cardOwnerBirth;
	}
}
