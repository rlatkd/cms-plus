package kr.or.kosa.cmsplusmain.domain.contract.exception;

import kr.or.kosa.cmsplusmain.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusmain.domain.base.error.exception.BusinessException;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingState;

public class InvalidContractStatusException extends BusinessException {
	public InvalidContractStatusException(String message) {
		super(message, ErrorCode.INVALID_CONTRACT_STATUS);
	}

	public InvalidContractStatusException(BillingState billingState) {
		super(billingState.getReason(), ErrorCode.INVALID_CONTRACT_STATUS);
	}
}
