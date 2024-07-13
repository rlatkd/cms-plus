package kr.or.kosa.cmsplusmain.domain.product.dto;

import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ProductListItemRes {

    private final Long productId;
    private final String productName;
    private final int productPrice;
    private final int contractNumber; // 계약 건수
    private final LocalDate productCreatedDate;
    private final String productMemo;

    public static ProductListItemRes fromEntity(Product product, int contractNumber) {
        return ProductListItemRes.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productPrice(product.getPrice())
                .contractNumber(contractNumber)
                .productCreatedDate(product.getCreatedDateTime().toLocalDate())
                .productMemo(product.getMemo())
                .build();
    }

}