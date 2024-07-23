package kr.or.kosa.cmsplusmain.domain.payment.entity.method;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/* 결제수단 */
@RequiredArgsConstructor
public enum PaymentMethod implements BaseEnum {
	CARD("카드", true, true),
	CMS("실시간CMS", false, true),
	ACCOUNT("계좌", false, false);

	private final String title;
	@Getter
	@JsonIgnore
	private final Boolean canCancel;		// 결제 취소 가능 여부
	@Getter
	@JsonIgnore
	private final Boolean canPayRealtime; 	// 실시간 결제 가능 여부

	public static class Const {
		public static final String CARD = "CARD";
		public static final String CMS = "CMS";
		public static final String ACCOUNT = "ACCOUNT";
	}

	public static PaymentMethod fromCode(String code) {
		for (PaymentMethod paymentMethod : PaymentMethod.values()) {
			if (paymentMethod.getCode().equals(code)) {
				return paymentMethod;
			}
		}
		return null;
	}

	@Override
	public String getCode() {
		return name();
	}

	@Override
	public String getTitle() {
		return title;
	}
}
