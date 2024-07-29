package kr.or.kosa.cmsplusbatch.domain.vendor.exception;

import kr.or.kosa.cmsplusbatch.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusbatch.domain.base.error.exception.BusinessException;

public class VendorEmailDuplicationException extends BusinessException {
	public VendorEmailDuplicationException(String message) {
		super(message, ErrorCode.VENDOR_EMAIL_DUPLICATION);
	}
}
