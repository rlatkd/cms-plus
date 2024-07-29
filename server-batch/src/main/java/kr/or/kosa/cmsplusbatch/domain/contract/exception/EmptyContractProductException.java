package kr.or.kosa.cmsplusbatch.domain.contract.exception;

import kr.or.kosa.cmsplusbatch.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusbatch.domain.base.error.exception.BusinessException;

public class EmptyContractProductException extends BusinessException {
	public EmptyContractProductException(String message) {
		super(message, ErrorCode.EMPTY_CONTRACT_PRODUCT);
	}
}
