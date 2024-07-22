package kr.or.kosa.cmsplusmain.domain.product.dto;

import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDto {
	private Long productId;
	private String productName;
	private Integer productPrice;

	public static ProductDto fromEntity(Product product) {
		return ProductDto.builder()
			.productId(product.getId())
			.productName(product.getName())
			.productPrice(product.getPrice())
			.build();
	}
}
