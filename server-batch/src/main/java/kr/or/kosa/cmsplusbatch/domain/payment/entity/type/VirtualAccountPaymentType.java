package kr.or.kosa.cmsplusbatch.domain.payment.entity.type;

import jakarta.persistence.DiscriminatorValue;
import kr.or.kosa.cmsplusbatch.domain.payment.converter.BankConverter;
import kr.or.kosa.cmsplusbatch.domain.payment.entity.Bank;
import lombok.Builder;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Comment("결제방식 - 가상계좌")
@Entity
@Getter
@DiscriminatorValue(PaymentType.Const.VIRTUAL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VirtualAccountPaymentType extends PaymentTypeInfo {

	@Comment("가상계좌 은행코드")
	@Convert(converter = BankConverter.class)
	@Column(name = "virtual_payment_bank_code", nullable = false, length = 20)
	@NotNull
	private Bank bank;

	@Comment("가상계좌 계좌번호")
	@Column(name = "virtual_payment_account_number", nullable = false, length = 14)
	@NotNull
	private String accountNumber;

	@Comment("가상계좌 예금주명")
	@Column(name = "virtual_payment_account_owner", nullable = false, length = 40)
	@NotNull
	private String accountOwner;

	@Builder
	public VirtualAccountPaymentType(Bank bank, String accountNumber, String accountOwner) {
		this.bank = bank;
		this.accountNumber = accountNumber;
		this.accountOwner = accountOwner;
	}
}
