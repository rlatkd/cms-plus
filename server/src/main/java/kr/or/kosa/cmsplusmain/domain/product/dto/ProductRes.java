package kr.or.kosa.cmsplusmain.domain.product.dto;

import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


 @Getter
 @Builder
 public class ProductDetail {

     private final Long productId;
     private final String productName;

     private final LocalDateTime createdDateTime;

     private final int productPrice;
     private final String productMemo;

     private final int contractNum; // 계약 건수

     public static ProductDetail fromEntity(Product product, int contractNum) {
         return ProductDetail.builder()
                 .productId(product.getId())
                 .productName(product.getName())
                 .createdDateTime(product.getCreatedDateTime())
                 .productPrice(product.getPrice())
                 .productMemo(product.getMemo())
                 .contractNum(contractNum)
                 .build();
     }

 }