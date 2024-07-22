package kr.or.kosa.cmsplusmain.domain.billing.exception;

import kr.or.kosa.cmsplusmain.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusmain.domain.base.error.exception.BusinessException;

public class InvalidUpdateBillingException extends BusinessException {
	public InvalidUpdateBillingException(String message) {
		super(message, ErrorCode.INVALID_UPDATE_BILLING);
	}
}
