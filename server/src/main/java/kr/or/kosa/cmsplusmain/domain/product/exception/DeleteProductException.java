package kr.or.kosa.cmsplusmain.domain.product.exception;

import kr.or.kosa.cmsplusmain.domain.base.error.ErrorCode;
import kr.or.kosa.cmsplusmain.domain.base.error.exception.BusinessException;

/*
* 상품 삭제 예외
* */
public class DeleteProductException extends BusinessException {
    public DeleteProductException() {
        super(ErrorCode.INVALID_PRODUCT_STATUS);
    }

    public DeleteProductException(String message) {
        super(message, ErrorCode.INVALID_PRODUCT_STATUS);
    }
}
