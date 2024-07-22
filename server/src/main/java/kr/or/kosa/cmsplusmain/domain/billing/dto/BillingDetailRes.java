package kr.or.kosa.cmsplusmain.domain.billing.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingType;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BillingDetailRes {

	private final Long memberId;							// 회원 ID
	private final String memberName;						// 회원 이름
	private final String memberPhone;						// 회원 휴대전화

	private final Long contractId;							// 계약 ID
	private final PaymentType paymentType;					// 결제방식
	private final PaymentMethod paymentMethod;				// 결제수단

	private final Long billingId;							// 청구 ID
	private final String billingName;						// 청구서명
	private final BillingType billingType;					// 청구타입
	private final BillingStatus billingStatus;				// 청구상태
	private final LocalDate billingCreatedDate;				// 청구 생성일
	private final LocalDate billingDate;					// 청구 결제일
	private final String billingMemo;						// 청구서 메시지

	private final List<BillingProductRes> billingProducts;	// 청구상품 목록
	private final Long billingPrice;						// 청구금액
	private final LocalDateTime invoiceSendDateTime;		// 청구서 발송 시각
	private final LocalDateTime paidDateTime;				// 결제된 시각

	private final Boolean canBeUpdated;						// 청구 수정 가능여부
	private final Boolean canBeDeleted;						// 청구 삭제 가능여부
	private final Boolean canSendInvoice;					// 청구서 발송 가능여부
	private final Boolean canCancelInvoice;					// 청구서 발송 취소 가능여부
	private final Boolean canBePaid;						// 실시간 결제 가능여부
	private final Boolean canPayCanceled;					// 결제 취소 가능여부

	public static BillingDetailRes fromEntity(Billing billing) {
		final Contract contract = billing.getContract();
		final Member member = contract.getMember();
		final Payment payment = contract.getPayment();

		final List<BillingProductRes> billingProductResList = billing.getBillingProducts()
			.stream()
			.map(BillingProductRes::fromEntity)
			.toList();

		return BillingDetailRes.builder()
			.memberId(member.getId())
			.memberName(member.getName())
			.memberPhone(member.getPhone())

			.contractId(contract.getId())
			.paymentType(payment.getPaymentType())
			.paymentMethod(payment.getPaymentMethod())

			.billingId(billing.getId())
			.billingName(billing.getInvoiceName())
			.billingType(billing.getBillingType())
			.billingStatus(billing.getBillingStatus())
			.billingCreatedDate(billing.getCreatedDateTime().toLocalDate())
			.billingDate(billing.getBillingDate())
			.billingMemo(billing.getInvoiceMessage())

			.billingProducts(billingProductResList)
			.billingPrice(billing.getBillingPrice())
			.invoiceSendDateTime(billing.getInvoiceSendDateTime())
			.paidDateTime(billing.getPaidDateTime())

			.canBeUpdated(billing.canBeUpdated())
			.canBeDeleted(billing.canBeDeleted())
			.canSendInvoice(billing.canSendInvoice())
			.canCancelInvoice(billing.canCancelInvoice())
			.canBePaid(billing.canPayRealtime())
			.canPayCanceled(billing.canCancelPaid())

			.build();
	}
}
