package kr.or.kosa.cmsplusbatch.domain.payment.entity.method;

import java.time.LocalDate;

import kr.or.kosa.cmsplusbatch.domain.payment.converter.BankConverter;
import kr.or.kosa.cmsplusbatch.domain.payment.entity.Bank;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Comment("결제수단 - CMS")
@Entity
@DiscriminatorValue(PaymentMethod.Const.CMS)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CmsPaymentMethod extends PaymentMethodInfo {

	// Converter 은행 코드로 저장됨
	@Comment("CMS 계좌 은행")
	@Column(name = "cms_account_bank", nullable = false)
	@Convert(converter = BankConverter.class)
	private Bank bank;

	@Comment("CMS 계좌번호")
	@Column(name = "cms_account_number", nullable = false, length = 15)
	@NotNull
	private String accountNumber;

	@Comment("CMS 계좌 소유주명")
	@Column(name = "cms_account_owner", nullable = false, length = 40)
	@NotNull
	private String accountOwner;

	@Comment("CMS 계좌 소유주 생년월일")
	@Column(name = "cms_owner_birth", nullable = false)
	@NotNull
	private LocalDate accountOwnerBirth;

	public void update(CmsPaymentMethod newCmsPaymentMethod) {
		this.bank = newCmsPaymentMethod.getBank();
		this.accountNumber = newCmsPaymentMethod.getAccountNumber();
		this.accountOwner = newCmsPaymentMethod.getAccountOwner();
		this.accountOwnerBirth = newCmsPaymentMethod.getAccountOwnerBirth();
	}
}

