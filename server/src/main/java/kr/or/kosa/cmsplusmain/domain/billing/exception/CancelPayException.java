package kr.or.kosa.cmsplusmain.domain.billing.exception;

public class CancelPayException extends RuntimeException {
	public CancelPayException() {
		super("실시간 결제가 불가능한 청구입니다.");
	}

	public CancelPayException(String message) {
		super(message);
	}
}
