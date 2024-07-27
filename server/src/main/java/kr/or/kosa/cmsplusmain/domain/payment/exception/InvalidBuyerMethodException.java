package kr.or.kosa.cmsplusmain.domain.payment.exception;

import kr.or.kosa.cmsplusmain.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusmain.domain.base.error.exception.BusinessException;

public class InvalidBuyerMethodException extends BusinessException {
	public InvalidBuyerMethodException(String message) {
		super(message, ErrorCode.INVALID_PAYMENT_METHOD);
	}
}
