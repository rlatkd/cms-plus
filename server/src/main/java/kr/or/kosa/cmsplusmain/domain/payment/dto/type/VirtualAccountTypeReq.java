package kr.or.kosa.cmsplusmain.domain.payment.dto.type;

import kr.or.kosa.cmsplusmain.domain.payment.entity.Bank;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.VirtualAccountPaymentType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VirtualAccountTypeReq extends PaymentTypeInfoReq {
	private final Bank bank;
	private final String accountOwner;

	@Builder
	public VirtualAccountTypeReq(Bank bank, String accountOwner) {
		super(PaymentType.VIRTUAL);
		this.bank = bank;
		this.accountOwner = accountOwner;
	}

	public VirtualAccountPaymentType toEntity(String accountNumber) {
		return VirtualAccountPaymentType.builder()
				.bank(this.bank)
				.accountNumber(accountNumber)
				.accountOwner(this.accountOwner)
				.build();
	}
}
