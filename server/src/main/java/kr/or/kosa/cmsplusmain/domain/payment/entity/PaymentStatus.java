package kr.or.kosa.cmsplusmain.domain.payment.entity;

import lombok.Getter;

@Getter
public enum PaymentStatus {
	ENABLED("사용"), DISABLED("미사용");

	private final String title;

	PaymentStatus(String title) {
		this.title = title;
	}
}
