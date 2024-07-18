package kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.dto;

import kr.or.kosa.cmsplusmain.domain.payment.dto.PaymentCreateReq;
import kr.or.kosa.cmsplusmain.domain.payment.dto.method.CMSMethodReq;
import kr.or.kosa.cmsplusmain.domain.payment.dto.method.CardMethodReq;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.AutoTypeReq;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Bank;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class SimpleConsentPaymentDTO {
    private PaymentMethod paymentMethod;
    private String cardNumber;
    private String expiryDate;
    private String cardHolder;
    private LocalDate cardOwnerBirth;
    private String bank;
    private String accountHolder;
    private LocalDate accountOwnerBirth;
    private String accountNumber;
    private String signatureUrl;

    public PaymentCreateReq toPaymentCreateReq() {
        AutoTypeReq paymentTypeInfoReq = AutoTypeReq.builder()
                .signImgUrl(signatureUrl)
                .simpleConsentReqDateTime(LocalDateTime.now())
                .build();

        if (paymentMethod == PaymentMethod.CARD) {
            CardMethodReq cardMethodReq = CardMethodReq.builder()
                    .cardNumber(cardNumber)
                    .cardMonth(Integer.parseInt(expiryDate.split("/")[0]))
                    .cardYear(Integer.parseInt(expiryDate.split("/")[1]))
                    .cardOwner(cardHolder)
                    .cardOwnerBirth(cardOwnerBirth)
                    .build();

            return PaymentCreateReq.builder()
                    .paymentTypeInfoReq(paymentTypeInfoReq)
                    .paymentMethodInfoReq(cardMethodReq)
                    .build();
        } else if (paymentMethod == PaymentMethod.CMS) {
            CMSMethodReq cmsMethodReq = CMSMethodReq.builder()
                    .bank(Bank.valueOf(bank))
                    .accountNumber(accountNumber)
                    .accountOwner(accountHolder)
                    .accountOwnerBirth(accountOwnerBirth)
                    .build();

            return PaymentCreateReq.builder()
                    .paymentTypeInfoReq(paymentTypeInfoReq)
                    .paymentMethodInfoReq(cmsMethodReq)
                    .build();
        }

        throw new IllegalStateException("Invalid payment method");
    }
}
