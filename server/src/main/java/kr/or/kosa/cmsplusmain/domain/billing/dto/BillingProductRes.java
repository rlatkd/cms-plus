package kr.or.kosa.cmsplusmain.domain.billing.dto;

import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingProduct;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BillingProductRes {
	private Long id;
	private Long billingStandardId;
	private String name;
	private	Integer price;
	private Integer quantity;

	public static BillingProductRes fromEntity(BillingProduct billingProduct) {
		return BillingProductRes.builder()
			.id(billingProduct.getId())
			.billingStandardId(billingProduct.getBillingStandard().getId())

			// 주의
			// 청구상품 과 청구 join 조회 필요
			.name(billingProduct.getProduct().getName())

			.price(billingProduct.getPrice())
			.quantity(billingProduct.getQuantity())
			.build();
	}
}
