package kr.or.kosa.cmspluspayment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.or.kosa.cmspluspayment.PaymentPayMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VirtualAccountPaymentDto extends PaymentDto {

    @JsonCreator
    public VirtualAccountPaymentDto(@JsonProperty("billingId") Long billingId, @JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("number") String number) {
        super(billingId, phoneNumber, PaymentPayMethod.VIRTUAL, number);
    }

}
