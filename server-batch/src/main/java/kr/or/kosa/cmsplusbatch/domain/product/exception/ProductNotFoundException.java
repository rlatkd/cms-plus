package kr.or.kosa.cmsplusbatch.domain.product.exception;

import kr.or.kosa.cmsplusbatch.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusbatch.domain.base.error.exception.BusinessException;

public class ProductNotFoundException extends BusinessException {
	public ProductNotFoundException(String message) {
		super(message, ErrorCode.PRODUCT_NOT_FOUND);
	}
}
