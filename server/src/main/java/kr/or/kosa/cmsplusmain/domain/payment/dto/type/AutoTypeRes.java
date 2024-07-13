package kr.or.kosa.cmsplusmain.domain.payment.dto.type;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import kr.or.kosa.cmsplusmain.domain.base.validator.HttpUrl;
import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AutoTypeRes extends PaymentTypeInfoRes {
	private String signImgUrl;
	private String consentImgUrl;
	private LocalDateTime simpleConsentReqDateTime;
	private ConsentStatus consentStatus;
}
