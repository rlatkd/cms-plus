package kr.or.kosa.cmsplusbatch.domain.vendor.exception;

import kr.or.kosa.cmsplusbatch.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusbatch.domain.base.error.exception.BusinessException;

public class VendorNotFoundException extends BusinessException {
	public VendorNotFoundException(String message) {
		super(message, ErrorCode.VENDOR_NOT_FOUND);
	}
}
