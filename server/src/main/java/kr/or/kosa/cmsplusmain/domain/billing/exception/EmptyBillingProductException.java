package kr.or.kosa.cmsplusmain.domain.billing.exception;

import kr.or.kosa.cmsplusmain.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusmain.domain.base.error.exception.BusinessException;

public class EmptyBillingProductException extends BusinessException {
	public EmptyBillingProductException(String message) {
		super(message, ErrorCode.EMPTY_BILLING_PRODUCT);
	}
}
