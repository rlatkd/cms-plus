package kr.or.kosa.cmsplusmain.domain.payment.entity;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.base.validator.PersonName;
import lombok.Getter;

@Comment("결제방식 - 가상계좌")
@Entity
@DiscriminatorValue(PaymentType.Values.VIRTUAL_ACCOUNT)
@Getter
public class VirtualAccountPayment extends Payment {

	@Comment("가상계좌 은행코드")
	@Column(name = "virtual_payment_bank_code", nullable = false, length = 20)
	@NotNull
	private Bank bank;

	@Comment("가상계좌 계좌번호")
	@Column(name = "virtual_payment_account_number", nullable = false, length = 14)
	@NotBlank
	private String accountNumber;

	@Comment("가상계좌 예금주명")
	@Column(name = "virtual_payment_account_owner", nullable = false, length = 40)
	@PersonName
	@NotNull
	private String accountOwner;

	@Override
	public PaymentMethod getPaymentMethod() {
		return null;
	}
}
