package kr.or.kosa.cmsplusmain.domain.payment.dto.method;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardMethodRes extends PaymentMethodInfoRes {
	private String cardNumber;
	private String cardOwner;
	private LocalDate cardOwnerBirth;
}
