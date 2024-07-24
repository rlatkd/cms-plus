package kr.or.kosa.cmsplusmain.domain.member.exception;

import kr.or.kosa.cmsplusmain.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusmain.domain.base.error.exception.BusinessException;

public class MemberNotFoundException extends BusinessException {
	public MemberNotFoundException(String message) {
		super(message, ErrorCode.MEMBER_NOT_FOUND);
	}
}
