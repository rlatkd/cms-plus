package kr.or.kosa.cmsplusmain.domain.contract.exception;

import kr.or.kosa.cmsplusmain.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusmain.domain.base.error.exception.BusinessException;

public class EmptyContractProductException extends BusinessException {
	public EmptyContractProductException(String message) {
		super(message, ErrorCode.EMPTY_CONTRACT_PRODUCT);
	}
}
