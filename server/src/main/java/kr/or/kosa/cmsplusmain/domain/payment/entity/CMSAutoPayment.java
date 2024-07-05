package kr.or.kosa.cmsplusmain.domain.payment.entity;

import java.time.LocalDate;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Comment("결제방식 - 자동결제 - CMS")
@Entity
@Table(name = "cms_auto_payment")
@DiscriminatorValue(PaymentType.Values.AUTO + "-" + PaymentMethod.Values.CMS)
@Getter
//TODO
public class CMSAutoPayment extends AutoPayment {

	@Column(name = "cms_auto_account_bank")
	private Bank bank;

	@Column(name = "cms_auto_account_number")
	private String accountNumber;

	@Column(name = "cms_auto_account_owner")
	private String accountOwner;

	@Column(name = "card_auto_owner_birth")
	private LocalDate accountOwnerBirth;
}
