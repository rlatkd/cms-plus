package kr.or.kosa.cmspluspayment.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import kr.or.kosa.cmspluspayment.PaymentPayMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "method",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AccountPaymentDto.class, name = "ACCOUNT"),
        @JsonSubTypes.Type(value = CardPaymentDto.class, name = "CARD"),
        @JsonSubTypes.Type(value = VirtualAccountPaymentDto.class, name = "VIRTUAL")
})
public abstract class PaymentDto { // 납부자결제(카드)-카드번호 | 납부자결제(계좌)-계좌번호 | 가상계좌-계좌번호

    private Long billingId;
    private String phoneNumber;
    private PaymentPayMethod method;
    private String number;

    public PaymentDto(Long billingId, String phoneNumber, PaymentPayMethod method, String number) {
        this.billingId = billingId;
        this.phoneNumber = phoneNumber;
        this.method = method;
        this.number = number;
    }

}