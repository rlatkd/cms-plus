package kr.or.kosa.cmsplusmain.domain.member.exception;

import kr.or.kosa.cmsplusmain.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusmain.domain.base.error.exception.BusinessException;

public class MemberDuplicationException extends BusinessException {
	public MemberDuplicationException(String message) {
		super(message, ErrorCode.MEMBER_DUPLICATION);
	}

	public MemberDuplicationException() {
		super(ErrorCode.MEMBER_DUPLICATION);
	}
}
