package kr.or.kosa.cmsplusmain.domain.billing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEntity;
import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "billing_product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BillingProduct extends BaseEntity {

	@Id
	@Column(name = "billing_product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "billing_id", nullable = false)
	private Billing billing;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "billing_product_extra_price")
	private int extraPrice;

	@Column(name = "billing_prodcut_discount_price")
	private int discountPrice;

	@Column(name = "billing_product_quantity")
	private int quantity;

	public int getTotalPrice() {
		return (product.getPrice() + extraPrice + discountPrice) * quantity;
	}
}
