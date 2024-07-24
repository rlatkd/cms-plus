package kr.or.kosa.cmsplusmain.domain.payment.exception;

import kr.or.kosa.cmsplusmain.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusmain.domain.base.error.exception.BusinessException;

public class InvalidPaymentTypeException extends BusinessException {
	public InvalidPaymentTypeException(String message) {
		super(message, ErrorCode.INVALID_PAYMENT_TYPE);
	}
}
