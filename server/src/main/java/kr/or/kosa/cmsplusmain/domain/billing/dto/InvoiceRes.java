package kr.or.kosa.cmsplusmain.domain.billing.dto;

import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberDto;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.PaymentTypeInfoRes;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentTypeInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InvoiceRes {
    private final Long billingId;
    private final String billingStatus;
    private final MemberDto member;
    private final String invoiceName;
    private final String invoiceMessage;
    private final Long  billingPrice;
    private final PaymentTypeInfoRes paymentType;

    public static InvoiceRes fromEntity(Billing billing, Member member, PaymentTypeInfoRes paymentType) {
        return InvoiceRes.builder()
                .billingId(billing.getId())
                .billingStatus(billing.getBillingStatus().getTitle())
                .member(MemberDto.fromEntity(member))
                .invoiceName(billing.getInvoiceName())
                .invoiceMessage(billing.getInvoiceMessage())
                .billingPrice(billing.getBillingPrice())
                .paymentType(paymentType)
                .build();
    }
}
