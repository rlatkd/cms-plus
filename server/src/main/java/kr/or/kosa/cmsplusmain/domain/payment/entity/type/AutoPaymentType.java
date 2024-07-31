package kr.or.kosa.cmsplusmain.domain.payment.entity.type;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.or.kosa.cmsplusmain.domain.base.validator.BlobUrl;
import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Comment("결제방식 - 자동결제")
@Entity
@Getter
@DiscriminatorValue(PaymentType.Const.AUTO)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AutoPaymentType extends PaymentTypeInfo {

	@Comment("회원 간편동의 서명 이미지 URL")
	@Column(name = "payment_sign_img_url", length = 2000)
	@BlobUrl @Setter
	private String signImgUrl;

	@Comment("고객이 등록한 동의서 이미지 URL")
	@Column(name = "payment_consent_img_url", length = 2000)
	@BlobUrl
	private String consentImgUrl;

	@Comment("간편동의 마지막 요청시간")
	@Column(name = "payment_simpconsent_request_date")
	private LocalDateTime simpleConsentReqDateTime;

	//TODO
	// 기본생성자 어노테니션 쓴 entity에서 builder 패턴을 적용해도 괜찮은가

	@Builder
	public AutoPaymentType(String signImgUrl, String consentImgUrl, LocalDateTime simpleConsentReqDateTime) {
		this.signImgUrl = signImgUrl;
		this.consentImgUrl = consentImgUrl;
		this.simpleConsentReqDateTime = simpleConsentReqDateTime;
	}

	/*
	 * 동의상태
	 *
	 * "동의" -> 간편동의 서명 이미지 or 고객이 등록한 동의서 이미지 중 1개 존재
	 * "대기중" -> 두 이미지 파일 없음 and 간편동의 마지막 요청시간이 존재
	 * "미동의" -> 이미지 두 개 모두 없고, 요청도 한 적이 없음
	 * */
	public ConsentStatus getConsentStatus() {
		if (!(StringUtils.isBlank(signImgUrl) && StringUtils.isBlank(consentImgUrl))) {
			return ConsentStatus.ACCEPT;
		} else if (simpleConsentReqDateTime != null) {
			return ConsentStatus.WAIT;
		} else {
			return ConsentStatus.NONE;
		}
	}

	/*
	 * 간편동의 발송
	 *
	 * 최종 발송 시각을 현재 시각으로 설정
	 * */
	public void sendSimpConsent() {
		this.simpleConsentReqDateTime = LocalDateTime.now();
	}
}
