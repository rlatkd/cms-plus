package kr.or.kosa.cmsplusmain.domain.contract.dto;

import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;
import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import kr.or.kosa.cmsplusmain.domain.product.validator.ProductName;
import lombok.Getter;

@Getter
public class ContractProductReq {
	private Long productId;
	private Integer price;
	private Integer quantity;

	public ContractProduct toEntity(Long contractId) {
		return ContractProduct.builder()
			.product(Product.of(productId))
			.contract(Contract.of(contractId))
			.price(price)
			.quantity(quantity)
			.build();
	}
}