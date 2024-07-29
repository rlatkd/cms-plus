package kr.or.kosa.cmsplusbatch.domain.member.exception;

import kr.or.kosa.cmsplusbatch.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusbatch.domain.base.error.exception.BusinessException;

public class EmailDuplicationException extends BusinessException {
	public EmailDuplicationException(String message) {
		super(message, ErrorCode.EMAIL_DUPLICATION);
	}
}
