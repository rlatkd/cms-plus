package kr.or.kosa.cmsplusmain.domain.kafka.dto.payment;

import kr.or.kosa.cmsplusmain.domain.kafka.PaymentPayMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class PaymentDto { // 납부자결제(카드)-카드번호 | 납부자결제(계좌)-계좌번호 | 가상계좌-계좌번호

    private Long billingId;
    private String phoneNumber; // 회원 전화번호
    private PaymentPayMethod method;
    private String number; // 카드번호 or 계좌번호

    public PaymentDto(Long billingId, String phoneNumber, PaymentPayMethod method, String number) {
        this.billingId = billingId;
        this.phoneNumber = phoneNumber;
        this.method = method;
        this.number = number;
    }

}
