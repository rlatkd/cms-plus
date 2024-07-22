package kr.or.kosa.cmsplusmain.domain.product.dto;

import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

// 임시 DTO로 삭제 예정

@Getter
@Builder
public class ProductDtoTmp {
    private Long productId;
    private String productName;
    private Integer productPrice;

    public static ProductDtoTmp fromEntity(Product product) {
        return ProductDtoTmp.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productPrice(product.getPrice())
                .build();
    }
}