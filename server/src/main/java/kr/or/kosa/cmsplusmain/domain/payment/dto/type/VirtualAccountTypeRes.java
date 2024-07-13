package kr.or.kosa.cmsplusmain.domain.payment.dto.type;

import kr.or.kosa.cmsplusmain.domain.payment.entity.Bank;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VirtualAccountTypeRes extends PaymentTypeInfoRes {
	private final Bank bank;
	private final String accountOwner;
	private final String accountNumber;

	@Builder
	public VirtualAccountTypeRes(Bank bank, String accountOwner, String accountNumber) {
		super(PaymentType.VIRTUAL);
		this.bank = bank;
		this.accountOwner = accountOwner;
		this.accountNumber = accountNumber;
	}
}
