package kr.or.kosa.cmsplusmain.domain.billing.exception;

import kr.or.kosa.cmsplusmain.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusmain.domain.base.error.exception.BusinessException;

public class InvalidBillingStatusException extends BusinessException {
	public InvalidBillingStatusException(String message) {
		super(message, ErrorCode.INVALID_BILLING_STATUS);
	}
}
