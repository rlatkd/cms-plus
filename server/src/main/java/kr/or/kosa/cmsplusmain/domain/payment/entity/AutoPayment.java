package kr.or.kosa.cmsplusmain.domain.payment.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Comment("결제방식 - 자동결제")
@Entity
@Table(name = "auto_payment")
@DiscriminatorValue(PaymentType.Values.AUTO)
@Getter
public class AutoPayment extends Payment {

	@Comment("자동결제 정보 회원 간편동의 서명 이미지 URL")
	@Column(name = "auto_payment_sign_img_url", nullable = true, length = 2000)
	@Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$")
	@Setter
	private String signImgUrl;

	@Comment("자동결제 정보 고객이 등록한 동의서 이미지 URL")
	@Column(name = "auto_payment_consent_img_url", nullable = true, length = 2000)
	@Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$")
	@Setter
	private String consentImgUrl;

	@Comment("자동결제 정보 간편동의 마지막 요청시간")
	@Column(name = "auto_payment_simpconsent_request_date", nullable = true)
	private LocalDateTime simpleConsentReqDateTime;
}
