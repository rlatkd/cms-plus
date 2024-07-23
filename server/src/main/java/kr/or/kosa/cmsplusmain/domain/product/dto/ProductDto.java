package kr.or.kosa.cmsplusmain.domain.product.dto;

import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDto {
	private Long productId;
	private String name;
	private Integer price;

	public static ProductDto fromEntity(Product product) {
		return ProductDto.builder()
			.productId(product.getId())
			.name(product.getName())
			.price(product.getPrice())
			.build();
	}
}
