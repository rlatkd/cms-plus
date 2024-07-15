package kr.or.kosa.cmsplusmain.domain.payment.dto.type;

import java.time.LocalDateTime;

import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.AutoPaymentType;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AutoTypeReq extends PaymentTypeInfoReq {
	private final String consentImgUrl;

	//TODO
	//간편동의 요청시간 필드에대한 상의가 필요

	@Builder
	public AutoTypeReq(String consentImgUrl, LocalDateTime simpleConsentReqDateTime ) {
		super(PaymentType.AUTO);
		this.consentImgUrl = consentImgUrl;
	}

	public AutoPaymentType toEntity() {
		return AutoPaymentType.builder()
				.consentImgUrl(this.consentImgUrl)
				.build();
	}
}
