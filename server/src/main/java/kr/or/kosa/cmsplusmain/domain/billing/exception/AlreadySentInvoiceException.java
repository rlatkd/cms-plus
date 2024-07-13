package kr.or.kosa.cmsplusmain.domain.billing.exception;

public class AlreadySentInvoiceException extends RuntimeException {
	public AlreadySentInvoiceException() {
		super("청구서가 이미 발송되었습니다.");
	}
}
