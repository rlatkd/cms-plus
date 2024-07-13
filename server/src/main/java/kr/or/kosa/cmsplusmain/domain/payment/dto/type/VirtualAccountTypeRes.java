package kr.or.kosa.cmsplusmain.domain.payment.dto.type;

import kr.or.kosa.cmsplusmain.domain.base.validator.PersonName;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Bank;
import kr.or.kosa.cmsplusmain.domain.payment.validator.AccountNumber;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VirtualAccountTypeRes extends PaymentTypeInfoRes {
	private Bank bank;
	private String accountOwner;
	private String accountNumber;
}
