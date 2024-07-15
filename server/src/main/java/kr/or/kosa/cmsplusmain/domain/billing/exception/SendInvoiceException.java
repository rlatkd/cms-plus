package kr.or.kosa.cmsplusmain.domain.billing.exception;

public class SendInvoiceException extends RuntimeException {
	public SendInvoiceException() {
		super("청구서가 이미 발송되었습니다.");
	}

	public SendInvoiceException(String message) {
		super(message);
	}
}
