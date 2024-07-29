package kr.or.kosa.cmsplusbatch.domain.vendor.exception;

import kr.or.kosa.cmsplusbatch.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusbatch.domain.base.error.exception.BusinessException;

public class VendorUsernameDuplicationException extends BusinessException {
	public VendorUsernameDuplicationException(String message) {
		super(message, ErrorCode.VENDOR_USERNAME_DUPLICATION);
	}
}
