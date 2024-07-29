package kr.or.kosa.cmsplusbatch.domain.billing.exception;

import kr.or.kosa.cmsplusbatch.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusbatch.domain.base.error.exception.BusinessException;
import kr.or.kosa.cmsplusbatch.domain.billing.entity.BillingState;

public class InvalidBillingStatusException extends BusinessException {
	public InvalidBillingStatusException(String message) {
		super(message, ErrorCode.INVALID_BILLING_STATUS);
	}

	public InvalidBillingStatusException(BillingState billingState) {
		super(billingState.getReason(), ErrorCode.INVALID_BILLING_STATUS);
	}
}
