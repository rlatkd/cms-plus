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
public class AccountPaymentDto extends PaymentDto {

    @JsonCreator
    public AccountPaymentDto(@JsonProperty("billingId") Long billingId, @JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("number") String number) {
        super(billingId, phoneNumber, PaymentPayMethod.ACCOUNT, number);
    }

}
