package kr.or.kosa.cmsplusmain.domain.payment.dto;

import java.time.LocalDate;

import kr.or.kosa.cmsplusmain.domain.payment.entity.Bank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CMSInfo {
	private Bank bank;

	private String accountNumber;

	private String accountOwner;

	private LocalDate accountOwnerBirth;
}
