package kr.or.kosa.cmsplusmain.domain.payment.dto.method;

import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CardMethodReq extends PaymentMethodInfoReq{
    private final String cardNumber;
    private final int cardMonth;
    private final int cardYear;
    private final String cardOwner;
    private final LocalDate cardOwnerBirth;

    @Builder
    public CardMethodReq(String cardNumber, int cardMonth, int cardYear, String cardOwner, LocalDate cardOwnerBirth) {
        super(PaymentMethod.CARD);
        this.cardNumber = cardNumber;
        this.cardMonth = cardMonth;
        this.cardYear = cardYear;
        this.cardOwner = cardOwner;
        this.cardOwnerBirth = cardOwnerBirth;
    }

}
