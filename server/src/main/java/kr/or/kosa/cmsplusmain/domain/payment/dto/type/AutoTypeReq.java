package kr.or.kosa.cmsplusmain.domain.payment.dto.type;

import java.time.LocalDateTime;

import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AutoTypeReq extends PaymentTypeInfoReq {
	private final String consentImgUrl;

	@Builder
	public AutoTypeReq(String signImgUrl, String consentImgUrl, LocalDateTime simpleConsentReqDateTime,
	    ConsentStatus consentStatus) {
		super(PaymentType.AUTO);
		this.consentImgUrl = consentImgUrl;
	}
}
