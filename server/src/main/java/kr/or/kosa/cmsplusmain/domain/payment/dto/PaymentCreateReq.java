package kr.or.kosa.cmsplusmain.domain.payment.dto;

import kr.or.kosa.cmsplusmain.domain.payment.dto.method.PaymentMethodInfoReq;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.PaymentTypeInfoReq;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentCreateReq {

    // 결제방식 info req
    PaymentTypeInfoReq paymentTypeInfoReq;

    // 결제수단 info req
    PaymentMethodInfoReq paymentMethodInfoReq;
}
