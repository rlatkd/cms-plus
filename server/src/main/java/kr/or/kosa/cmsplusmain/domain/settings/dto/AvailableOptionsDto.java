package kr.or.kosa.cmsplusmain.domain.settings.dto;

import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentMethod;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class AvailableOptionsDto {
    private Set<PaymentMethod> availablePaymentMethods;
    private List<ProductDto> availableProducts;

    public AvailableOptionsDto(Set<PaymentMethod> paymentMethods, List<ProductDto> products) {
        this.availablePaymentMethods = paymentMethods;
        this.availableProducts = products;
    }
}
