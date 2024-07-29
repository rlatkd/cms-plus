package kr.or.kosa.cmsplusbatch.domain.product.entity;

import kr.or.kosa.cmsplusbatch.domain.base.entity.BaseEntity;
import kr.or.kosa.cmsplusbatch.domain.vendor.entity.Vendor;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.Comment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Comment("고객이 등록한 상품")
@Entity
@Table(name = "product")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("상품을 등록한 고객")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendor_id", updatable = false, nullable = false)
	@NotNull
	private Vendor vendor;

	@Comment("상품 상태")
	@Enumerated(EnumType.STRING)
	@Column(name = "product_status", nullable = false)
	@NotNull
	@Builder.Default
	private ProductStatus status = ProductStatus.ENABLED;

	@Comment("상품 이름")
	@Column(name = "product_name", nullable = false, length = 20)
	@NotNull
	private String name;

	@Comment("상품 금액")
	@Column(name = "product_price", nullable = false)
	@Setter
	private int price;

	@Comment("상품 비고")
	@Column(name = "product_memo", length = 2000)
	@Setter
	private String memo;

	// id값만 가진 빈 product를 생성
	public static Product of(Long id) {
		Product emptyProduct = new Product();
		emptyProduct.id = id;
		return emptyProduct;
	}
}
