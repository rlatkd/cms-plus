package kr.or.kosa.cmsplusmain.domain.payment.exception;

import kr.or.kosa.cmsplusmain.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusmain.domain.base.error.exception.BusinessException;

public class PaymentNotFoundException extends BusinessException {
	public PaymentNotFoundException(String message) {
		super(message, ErrorCode.PAYMENT_NOT_FOUND);
	}
}
