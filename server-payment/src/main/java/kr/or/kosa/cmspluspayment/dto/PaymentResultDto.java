package kr.or.kosa.cmspluspayment.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentResultDto {

    private Long billingId;
    private Boolean result;

}
