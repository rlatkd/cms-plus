package kr.or.kosa.cmsplusmain.domain.contract.exception;

import kr.or.kosa.cmsplusmain.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusmain.domain.base.error.exception.BusinessException;

public class ContractNotFoundException extends BusinessException {
	public ContractNotFoundException(String message) {
		super(message, ErrorCode.CONTRACT_NOT_FOUND);
	}
}
