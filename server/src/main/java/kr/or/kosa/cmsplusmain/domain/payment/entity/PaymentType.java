package kr.or.kosa.cmsplusmain.domain.payment.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

/* 결제방식 */
@Getter
public enum PaymentType {
	AUTO("자동결제", Values.AUTO, List.of(PaymentMethod.CMS, PaymentMethod.CARD)),
	BUYER("납부자결제", Values.BUYER, List.of(PaymentMethod.CARD, PaymentMethod.ACCOUNT)),
	VIRTUAL("가상계좌", Values.VIRTUAL_ACCOUNT, Collections.emptyList());

	private final String title;
	private final String name;
	private final List<PaymentMethod> availablePaymentMethods;

	PaymentType(String title, String name, List<PaymentMethod> availablePaymentMethods) {
		this.title = title;
		this.name = name;
		this.availablePaymentMethods = availablePaymentMethods;
	}

	/* DiscriminatorValue(PaymentType.Values...)에 사용됨 */
	public static class Values {
		public static final String AUTO = "AUTO_PAYMENT";
		public static final String VIRTUAL_ACCOUNT = "VIRTUAL_ACCOUNT_PAYMENT";
		public static final String BUYER = "BUYER_PAYMENT";
	}

	public static PaymentType of(String name) {
		if (name == null || name.isEmpty()) return null;
		return Arrays.stream(PaymentType.values())
					.filter(p -> name.contains(p.name))
			.findAny().orElseThrow();
	}
}
