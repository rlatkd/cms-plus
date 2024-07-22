package kr.or.kosa.cmsplusmain.domain.kafka.dto.payment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentResultDto {

    private Long billingId;
    private String phoneNumber;
    private String result;

}
