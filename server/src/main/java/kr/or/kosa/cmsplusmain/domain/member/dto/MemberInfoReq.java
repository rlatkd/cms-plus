package kr.or.kosa.cmsplusmain.domain.member.dto;

import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.member.entity.MemberStatus;
import kr.or.kosa.cmsplusmain.domain.messaging.MessageSendMethod;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberInfoReq {
    private int memberId;
    private String memberName;
    private String memberPhone;
    private LocalDate memberEnrollDate;
    private String memberHomePhone;
    private String memberEmail;
    private String memberAddress;
    private String memberMemo;
    private MessageSendMethod invoiceSendMethod;
    private boolean autoInvoiceSend;
    private boolean autoBilling;

    public Member toEntity(Long vendorId) {
        return Member.builder()
                .vendor(Vendor.of(vendorId))
                .status(MemberStatus.DISABLED)
                .name(memberName)
                .email(memberEmail)
                .phone(memberPhone)
                .homePhone(memberHomePhone)
                .address(memberAddress)
                .memo(memberMemo)
                .enrollDate(memberEnrollDate)
                .invoiceSendMethod(invoiceSendMethod)
                .autoInvoiceSend(autoInvoiceSend)
                .autoBilling(autoBilling)
                .build();
    }
}
