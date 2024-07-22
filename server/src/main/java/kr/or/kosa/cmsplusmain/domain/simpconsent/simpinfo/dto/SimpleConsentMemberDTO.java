package kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.dto;

import kr.or.kosa.cmsplusmain.domain.member.entity.Address;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.member.entity.MemberStatus;
import kr.or.kosa.cmsplusmain.domain.kafka.MessageSendMethod;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@Builder
public class SimpleConsentMemberDTO {
    private String name;
    private String phone;
    private String homePhone;
    private String email;
    private String zipcode;
    private String address;
    private String addressDetail;
    private String memo;
    private LocalDate enrollDate;
    private MessageSendMethod invoiceSendMethod;
    private boolean autoInvoiceSend;
    private boolean autoBilling;

    public Member toMemberEntity(Long vendorId) {
        return Member.builder()
                .vendor(Vendor.of(vendorId))
                .status(MemberStatus.ENABLED)
                .name(name)
                .email(email)
                .phone(phone)
                .homePhone(homePhone)
                .address(new Address(zipcode, address, addressDetail))
                .memo(memo)
                .invoiceSendMethod(invoiceSendMethod != null ? invoiceSendMethod : MessageSendMethod.EMAIL) // 기본값 설정
                .enrollDate(enrollDate != null ? enrollDate : LocalDate.now()) // 기본값 설정
                .autoInvoiceSend(autoInvoiceSend)
                .autoBilling(autoBilling)
                .build();
    }
}
