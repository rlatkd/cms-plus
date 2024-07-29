package kr.or.kosa.cmsplusbatch.domain.product.entity;

import lombok.Getter;

@Getter
public enum ProductStatus {
	ENABLED("사용"), DISABLED("미사용");

	private final String title;

	ProductStatus(String title) {
		this.title = title;
	}
}
