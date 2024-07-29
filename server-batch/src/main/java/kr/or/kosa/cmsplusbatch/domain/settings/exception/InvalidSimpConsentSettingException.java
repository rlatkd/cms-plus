package kr.or.kosa.cmsplusbatch.domain.settings.exception;

import kr.or.kosa.cmsplusbatch.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusbatch.domain.base.error.exception.BusinessException;

public class InvalidSimpConsentSettingException extends BusinessException {
	public InvalidSimpConsentSettingException(String message) {
		super(message, ErrorCode.INVALID_SIMP_CONSENT_SETTING);
	}
}
