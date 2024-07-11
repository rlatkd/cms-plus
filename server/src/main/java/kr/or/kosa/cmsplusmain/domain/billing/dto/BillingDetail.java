package kr.or.kosa.cmsplusmain.domain.billing.dto;

import java.time.LocalDate;
import java.util.List;

import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStandard;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingType;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
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

	public static BillingDetail fromEntity(Billing billing) {
		BillingStandard billingStandard = billing.getBillingStandard();
		Contract contract = billingStandard.getContract();
		Member member = contract.getMember();
		Payment payment = contract.getPayment();

		List<BillingProductRes> billingProductResList = billingStandard.getBillingProducts()
			.stream()
			.map(BillingProductRes::fromEntity)
			.toList();

		return BillingDetail.builder()
			.memberId(member.getId())
			.memberName(member.getName())
			.memberPhone(member.getPhone())

			.contractId(contract.getId())
			.paymentType(payment.getPaymentType())
			.paymentMethod(payment.getPaymentMethod())

			.billingId(billing.getId())
			.billingName(billing.getBillingName())
			.billingType(billingStandard.getType())
			.billingCreatedDate(billing.getCreatedDateTime().toLocalDate())
			.billingDate(billing.getBillingDate())
			.billingMemo(billing.getMemo())

			.billingProducts(billingProductResList)
			.billingPrice(billingStandard.getBillingPrice())
			.build();
	}
}
