package kr.or.kosa.cmsplusmain.domain.billing.dto;

import java.time.LocalDate;
import java.util.List;

import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingType;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BillingDetail {

	private final Long memberId;
	private final String memberName;
	private final String memberPhone;

	private final Long contractId;
	private final PaymentType paymentType;
	private final PaymentMethod paymentMethod;

	private final Long billingId;
	private final String billingName;
	private final BillingType billingType;
	private final LocalDate billingCreatedDate;
	private final LocalDate billingDate;
	private final String billingMemo;

	private final List<BillingProductRes> billingProducts;
	private final Long billingPrice;
}
