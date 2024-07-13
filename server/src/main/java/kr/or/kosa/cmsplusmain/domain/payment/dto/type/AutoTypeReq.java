package kr.or.kosa.cmsplusmain.domain.payment.dto.type;

import java.time.LocalDateTime;

import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AutoTypeReq extends PaymentTypeInfoReq {
	private final String signImgUrl;
	private final String consentImgUrl;
	private final LocalDateTime simpleConsentReqDateTime;
	private final ConsentStatus consentStatus;

	@Builder
	public AutoTypeReq(String signImgUrl, String consentImgUrl, LocalDateTime simpleConsentReqDateTime,
	    ConsentStatus consentStatus) {
		super(PaymentType.AUTO);
		this.signImgUrl = signImgUrl;
		this.consentImgUrl = consentImgUrl;
		this.simpleConsentReqDateTime = simpleConsentReqDateTime;
		this.consentStatus = consentStatus;
	}
}
