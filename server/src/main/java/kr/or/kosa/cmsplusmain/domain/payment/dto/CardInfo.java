package kr.or.kosa.cmsplusmain.domain.payment.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardInfo {
	private String cardNumber;
	private String cardOwner;
	private LocalDate cardOwnerBirth;
}
