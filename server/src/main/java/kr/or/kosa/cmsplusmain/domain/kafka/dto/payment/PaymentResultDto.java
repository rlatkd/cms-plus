package kr.or.kosa.cmsplusmain.domain.kafka.dto.payment;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentResultDto {

    @NotNull
    private Long billingId;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String result;

}
