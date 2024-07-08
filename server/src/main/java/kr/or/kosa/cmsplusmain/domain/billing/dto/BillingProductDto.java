package kr.or.kosa.cmsplusmain.domain.billing.dto;

import com.querydsl.core.annotations.QueryProjection;

import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingProduct;
import lombok.Builder;
import lombok.Getter;

public class BillingProductDto {

	@Getter
	public static class Res {
		private final Long billingProductId;
		private final String name;
		private final Integer extraPrice;
		private final Integer discountPrice;
		private final Integer quantity;
		private final Integer totalPrice;

		@QueryProjection
		@Builder
		public Res(Long billingProductId, String name, Integer extraPrice, Integer discountPrice,
			Integer quantity, Integer totalPrice)
		{
			this.billingProductId = billingProductId;
			this.name = name;
			this.extraPrice = extraPrice;
			this.discountPrice = discountPrice;
			this.quantity = quantity;
			this.totalPrice = totalPrice;
		}

		public static BillingProductDto.Res fromEntity(BillingProduct billingProduct) {
			return Res.builder()
				.billingProductId(billingProduct.getId())
				.name(billingProduct.getProductName())
				.extraPrice(billingProduct.getExtraPrice())
				.discountPrice(billingProduct.getDiscountPrice())
				.quantity(billingProduct.getQuantity())
				.totalPrice(billingProduct.getTotalPrice())
				.build();
		}
	}
}
