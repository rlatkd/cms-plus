package kr.or.kosa.cmsplusmain.domain.billing.dto;

import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingProduct;
import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import lombok.Getter;

@Getter
public class BillingProductReq {
	private Long productId;
	private Integer price;
	private Integer quantity;

	public BillingProduct toEntity() {
		return BillingProduct.builder()
			.product(Product.of(productId))
			.price(price)
			.quantity(quantity)
			.build();
	}
}
