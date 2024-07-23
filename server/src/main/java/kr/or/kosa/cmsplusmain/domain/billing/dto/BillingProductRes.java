package kr.or.kosa.cmsplusmain.domain.billing.dto;

import com.querydsl.core.annotations.QueryProjection;

import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingProduct;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BillingProductRes {
	private final Long billingProductId;		// 청구상품 ID
	private final Long billingId;				// 청구 ID
	private final Long productId;				// 상품 ID
	private final String name;					// 상품명
	private final Integer price;				// 청구상품 가격
	private final Integer quantity;				// 청구상품 수량

	@Builder
	@QueryProjection
	public BillingProductRes(Long billingProductId, Long billingId, Long productId, String name, Integer price,
		Integer quantity) {
		this.billingProductId = billingProductId;
		this.billingId = billingId;
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public static BillingProductRes fromEntity(BillingProduct billingProduct) {
		return BillingProductRes.builder()
			.billingProductId(billingProduct.getId())
			.productId(billingProduct.getProduct().getId())
			.name(billingProduct.getName())
			.price(billingProduct.getPrice())
			.quantity(billingProduct.getQuantity())
			.build();
	}
}
