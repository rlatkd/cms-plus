package kr.or.kosa.cmsplusbatch.domain.product.exception;

/*
* 상품 삭제 예외
* */
public class DeleteProductException extends RuntimeException{
    public DeleteProductException() {
        super("상품 삭제는 등록되어 있는 상품이 2개 이상일때만 가능합니다.");
    }
}
