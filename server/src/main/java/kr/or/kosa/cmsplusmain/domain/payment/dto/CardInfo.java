package kr.or.kosa.cmsplusmain.domain.payment.dto;

import java.time.LocalDate;

import kr.or.kosa.cmsplusmain.domain.base.validator.PersonName;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardInfo {
	private String cardNumber;
	@PersonName
	private String cardOwner;
	private LocalDate cardOwnerBirth;
}
