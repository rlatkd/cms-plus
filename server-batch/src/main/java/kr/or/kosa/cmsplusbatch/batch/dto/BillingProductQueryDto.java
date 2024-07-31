// package kr.or.kosa.cmsplusbatch.batch.dto;

// import com.querydsl.core.annotations.QueryProjection;
// import lombok.Getter;

// @Getter
// public class BillingProductQueryDto {
//     private final Long id;
//     private final Long productId;
//     private final String billingProductName;
//     private final Integer billingProductPrice;
//     private final Integer billingProductQuantity;
//     private final Long billingId;

//     @QueryProjection
//     public BillingProductQueryDto(Long id, Long productId, String billingProductName, Integer billingProductPrice, Integer billingProductQuantity, Long billingId) {
//         this.id = id;
//         this.productId = productId;
//         this.billingProductName = billingProductName;
//         this.billingProductPrice = billingProductPrice;
//         this.billingProductQuantity = billingProductQuantity;
//         this.billingId = billingId;
//     }

//     public Long getBillingId() {
//         return billingId;
//     }
// }
