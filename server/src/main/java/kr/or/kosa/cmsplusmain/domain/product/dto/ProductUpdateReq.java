package kr.or.kosa.cmsplusmain.domain.product.dto;

import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import kr.or.kosa.cmsplusmain.domain.product.validator.ProductMemo;
import kr.or.kosa.cmsplusmain.domain.product.validator.ProductPrice;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;
import lombok.Getter;

@Getter
public class ProductUpdateReq {

    @NotNull
    @ProductPrice
    private int productPrice;

    @ProductMemo
    private String productMemo;

    public Product toEntity(Vendor vendor) {
        return Product.builder()
                .vendor(vendor)
                .price(productPrice)
                .memo(productMemo)
                .build();
    }

}
