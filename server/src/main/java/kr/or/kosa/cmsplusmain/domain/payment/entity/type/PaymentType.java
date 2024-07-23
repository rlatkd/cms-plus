package kr.or.kosa.cmsplusmain.domain.payment.entity.type;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEnum;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/* 결제방식 */
@RequiredArgsConstructor
public enum PaymentType implements BaseEnum {
	AUTO("자동결제", List.of(PaymentMethod.CMS, PaymentMethod.CARD)),
	BUYER("납부자결제", List.of(PaymentMethod.CARD, PaymentMethod.ACCOUNT)),
	VIRTUAL("가상계좌", Collections.emptyList());

	private final String title;
	private final List<PaymentMethod> availablePaymentMethods;

	public static class Const {
		public static final String AUTO = "AUTO";
		public static final String BUYER = "BUYER";
		public static final String VIRTUAL = "VIRTUAL";
	}

	/* 자동결제수단 확인용 */
	public static List<PaymentMethod> getAutoPaymentMethods() {
		return PaymentType.AUTO.availablePaymentMethods;
	}

	/* 납부자결제 수단 확인용 */
	public static List<PaymentMethod> getBuyerPaymentMethods() {
		return PaymentType.BUYER.availablePaymentMethods;
	}

	public static PaymentType fromCode(String code) {
		for (PaymentType paymentType : PaymentType.values()) {
			if (paymentType.name().equals(code)) {
				return paymentType;
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
