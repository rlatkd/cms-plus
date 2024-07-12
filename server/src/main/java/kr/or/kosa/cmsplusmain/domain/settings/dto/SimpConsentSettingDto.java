package kr.or.kosa.cmsplusmain.domain.settings.dto;

import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import lombok.Data;

import java.util.Set;

@Data
public class SimpConsentSettingDto {
    private Long id;
    private String vendorUsername;
    private Set<PaymentMethod> paymentMethods;
    private Set<Long> productIds;

}