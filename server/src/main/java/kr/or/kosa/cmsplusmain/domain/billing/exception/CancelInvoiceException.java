package kr.or.kosa.cmsplusmain.domain.billing.exception;

public class CancelInvoiceException extends RuntimeException {
	public CancelInvoiceException() {
		super("청구서 발송 취소가 불가능한 상태입니다.");
	}

	public CancelInvoiceException(String message) {
		super(message);
	}
}
