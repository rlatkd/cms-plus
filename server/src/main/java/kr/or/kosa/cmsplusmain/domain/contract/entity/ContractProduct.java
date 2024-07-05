package kr.or.kosa.cmsplusmain.domain.contract.entity;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Comment("계약 - 상품 중계테이블. 상품 변동에 영향을 받지 않는다.")
@Entity
@Table(name = "contract_product")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContractProduct extends BaseEntity {

	@Id
	@Column(name = "contract_product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "contract_id")
	private Contract contract;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Comment("계약_상품 이름")
	@Column(name = "contract_product_name", nullable = false)
	private String name;

	@Comment("계약_상품 가격")
	@Column(name = "contract_product_price", nullable = false)
	private int price;

	@Comment("계약_상품 수량")
	@Column(name = "contract_product_quantity", nullable = false)
	private int quantity;
}
