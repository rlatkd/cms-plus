package kr.or.kosa.cmsplusmain.domain.kafka.dto.payment;

import kr.or.kosa.cmsplusmain.domain.kafka.PaymentPayMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VirtualAccountPaymentDto extends PaymentDto {

    public VirtualAccountPaymentDto(Long billingId, String phoneNumber, String number) {
        super(billingId, phoneNumber, PaymentPayMethod.VIRTUAL, number);
    }

}
