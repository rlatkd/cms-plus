package kr.or.kosa.cmsplusmain.domain.billing.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import kr.or.kosa.cmsplusmain.domain.base.dto.ButtonStateRes;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingState;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingType;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberDto;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BillingDetailRes {

	private final MemberDto member;
	private final BillingDto billing;

	private final Long contractId;							// 계약 ID
	private final PaymentType paymentType;					// 결제방식
	private final PaymentMethod paymentMethod;				// 결제수단

	private final Map<BillingState.Field, BillingState> fieldToState;		// 동작 별 가능 상태 및 이유

	public static BillingDetailRes fromEntity(Billing mBilling) {
		final Contract contract = mBilling.getContract();
		final Member mMember = contract.getMember();
		final Payment mPayment = contract.getPayment();

		return BillingDetailRes.builder()
			.member(MemberDto.fromEntity(mMember))
			.billing(BillingDto.fromEntity(mBilling))
			.contractId(contract.getId())
			.paymentType(mPayment.getPaymentType())
			.paymentMethod(mPayment.getPaymentMethod())
			.build();
	}

	public static BillingDetailRes fromEntity(Billing mBilling, Map<BillingState.Field, BillingState> fieldToState) {
		final Contract contract = mBilling.getContract();
		final Member mMember = contract.getMember();
		final Payment mPayment = contract.getPayment();

		return BillingDetailRes.builder()
			.member(MemberDto.fromEntity(mMember))
			.billing(BillingDto.fromEntity(mBilling))
			.contractId(contract.getId())
			.paymentType(mPayment.getPaymentType())
			.paymentMethod(mPayment.getPaymentMethod())
			.fieldToState(fieldToState)
			.build();
	}
}
