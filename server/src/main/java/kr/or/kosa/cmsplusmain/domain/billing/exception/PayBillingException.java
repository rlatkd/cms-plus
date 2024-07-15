package kr.or.kosa.cmsplusmain.domain.billing.exception;

public class PayBillingException extends RuntimeException {
	public PayBillingException() {
		super("실시간 결제가 불가능한 청구입니다.");
	}

	public PayBillingException(String message) {
		super(message);
	}
}
