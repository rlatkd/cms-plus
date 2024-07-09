package kr.or.kosa.cmsplusmain.domain.product.dto;

import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class ProductDetail {

    private final Long productId;
    private final String productName;

    private final LocalDateTime createdDateTime;

    private final int productPrice;
    private final String productMemo;

    private final int contractNum;

    @Builder
    public ProductDetail(Long productId, String productName, LocalDateTime createdDateTime, int productPrice, String productMemo, int contractNum) {
        this.productId = productId;
        this.productName = productName;
        this.createdDateTime = createdDateTime;
        this.productPrice = productPrice;
        this.productMemo = productMemo;
        this.contractNum = contractNum;
    }

    public static ProductDetail fromEntity(Product product) {
        if (product == null) {
            return null;
        }



    }

}
