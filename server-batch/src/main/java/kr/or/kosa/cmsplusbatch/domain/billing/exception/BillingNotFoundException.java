package kr.or.kosa.cmsplusbatch.domain.billing.exception;


import kr.or.kosa.cmsplusbatch.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusbatch.domain.base.error.exception.BusinessException;

public class BillingNotFoundException extends BusinessException {
	public BillingNotFoundException(String message) {
		super(message, ErrorCode.BILLING_NOT_FOUND);
	}
}
