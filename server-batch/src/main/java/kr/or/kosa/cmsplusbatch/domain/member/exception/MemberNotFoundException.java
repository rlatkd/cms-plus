package kr.or.kosa.cmsplusbatch.domain.member.exception;

import kr.or.kosa.cmsplusbatch.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusbatch.domain.base.error.exception.BusinessException;

public class MemberNotFoundException extends BusinessException {
	public MemberNotFoundException(String message) {
		super(message, ErrorCode.MEMBER_NOT_FOUND);
	}
}
