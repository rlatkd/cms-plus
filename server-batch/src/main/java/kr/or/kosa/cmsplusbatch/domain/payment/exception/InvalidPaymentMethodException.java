package kr.or.kosa.cmsplusbatch.domain.payment.exception;

import kr.or.kosa.cmsplusbatch.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusbatch.domain.base.error.exception.BusinessException;

public class InvalidPaymentMethodException extends BusinessException {
	public InvalidPaymentMethodException(String message) {
		super(message, ErrorCode.INVALID_PAYMENT_METHOD);
	}
}
