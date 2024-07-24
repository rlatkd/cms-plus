package kr.or.kosa.cmsplusmain.domain.settings.dto;

import kr.or.kosa.cmsplusmain.domain.product.dto.ProductDetailRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import kr.or.kosa.cmsplusmain.domain.product.dto.ProductListItemRes;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
public class AvailableOptionsDto {
    private Set<PaymentMethod> availablePaymentMethods;
    private List<ProductDetailRes> availableProducts;

    public AvailableOptionsDto(Set<PaymentMethod> availablePaymentMethods, List<ProductDetailRes> availableProducts) {
        this.availablePaymentMethods = availablePaymentMethods;
        this.availableProducts = availableProducts;
    }
}