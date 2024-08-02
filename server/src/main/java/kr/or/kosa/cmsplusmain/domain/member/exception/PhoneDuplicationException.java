package kr.or.kosa.cmsplusmain.domain.member.exception;

import kr.or.kosa.cmsplusmain.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusmain.domain.base.error.exception.BusinessException;

public class PhoneDuplicationException extends BusinessException {
	public PhoneDuplicationException(String message) {
		super(message, ErrorCode.MEMBER_PHONE_DUPLICATION);
	}
}
