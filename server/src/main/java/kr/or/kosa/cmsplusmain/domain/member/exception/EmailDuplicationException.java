package kr.or.kosa.cmsplusmain.domain.member.exception;

import kr.or.kosa.cmsplusmain.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusmain.domain.base.error.exception.BusinessException;

public class EmailDuplicationException extends BusinessException {
	public EmailDuplicationException(String message) {
		super(message, ErrorCode.EMAIL_DUPLICATION);
	}
}
