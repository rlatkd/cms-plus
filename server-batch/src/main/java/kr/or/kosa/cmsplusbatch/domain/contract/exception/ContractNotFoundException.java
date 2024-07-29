package kr.or.kosa.cmsplusbatch.domain.contract.exception;

import kr.or.kosa.cmsplusbatch.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusbatch.domain.base.error.exception.BusinessException;

public class ContractNotFoundException extends BusinessException {
	public ContractNotFoundException(String message) {
		super(message, ErrorCode.CONTRACT_NOT_FOUND);
	}
}
