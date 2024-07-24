package kr.or.kosa.cmsplusmain.domain.base.error.exception;

import kr.or.kosa.cmsplusmain.domain.base.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {
	public EntityNotFoundException(String message) {
		super(message, ErrorCode.ENTITY_NOT_FOUND);
	}
}
