package kr.or.kosa.cmsplusmain.domain.billing.entity;

import lombok.ToString;
import org.hibernate.annotations.Comment;

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
@ToString // 임시로
public class BillingProduct extends BaseEntity {

	@Id
	@Column(name = "billing_product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("청구상품의 청구기준")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "billing_standard_id")
	private BillingStandard billingStandard;

	/*
	 * 청구 상품이 사용될 때 상품의 이름이 항상 같이 사용된다.
	 * fetch eager -> 쿼리 횟수 감소
	 * */
	@Comment("청구상품의 상품")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "product_id")
	private Product product;

	@Comment("청구상품의 가격")
	@Column(name = "billing_product_price")
	private int price;

	@Comment("청구상품의 수량")
	@Column(name = "billing_product_quantity")
	//TODO 최대개수??
	private int quantity;

	public long getTotalPrice() {
		return (long)price * quantity;
	}
}
