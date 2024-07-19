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
public class CardPaymentDto extends PaymentDto {

    @JsonCreator
    public CardPaymentDto(@JsonProperty("billingId") Long billingId, @JsonProperty("number") String number) {
        super(billingId, PaymentPayMethod.CARD, number);
    }

}
