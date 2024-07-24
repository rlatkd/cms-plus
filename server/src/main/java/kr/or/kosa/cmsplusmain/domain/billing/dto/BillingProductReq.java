package kr.or.kosa.cmsplusmain.domain.billing.dto;

import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingProduct;
import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import kr.or.kosa.cmsplusmain.domain.product.validator.ProductPrice;
import kr.or.kosa.cmsplusmain.domain.product.validator.ProductQuantity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BillingProductReq {
	@NotNull
	private Long productId;			// 상품 ID
	@ProductPrice @NotNull
	private Integer price;			// 청구 상품 가격
	@ProductQuantity @NotNull
	private Integer quantity;		// 청구 상품 개수

	public BillingProduct toEntity(String productName) {
		return BillingProduct.builder()
			.product(Product.of(productId))
			.name(productName)
			.price(price)
			.quantity(quantity)
			.build();
	}
}
