package kr.or.kosa.cmsplusmain.domain.settings.exception;

import kr.or.kosa.cmsplusmain.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusmain.domain.base.error.exception.BusinessException;

public class InvalidSimpConsentSettingException extends BusinessException {
	public InvalidSimpConsentSettingException(String message) {
		super(message, ErrorCode.INVALID_SIMP_CONSENT_SETTING);
	}
}
