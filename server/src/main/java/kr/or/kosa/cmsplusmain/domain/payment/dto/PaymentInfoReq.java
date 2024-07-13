package kr.or.kosa.cmsplusmain.domain.payment.dto;

import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentType;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class PaymentInfoReq {
    private PaymentType paymentType;
    private PaymentMethod paymentMethod;
    private String consentImgUrl;

    // Type = 자동결제 , Method = CMS
    private CMSInfo cmsInfo;

    // Type = 자동결제 , Method = CARD
    private CardInfo cardInfo;

    // Type = 납부자결제 , Method = (CARD, ACCOUNT)
    private Set<PaymentMethod> availableMethods = new HashSet<>();

    // Type = 가상계좌 , Method = ""
    private VirtualAccountInfo virtualAccountInfo;
}
