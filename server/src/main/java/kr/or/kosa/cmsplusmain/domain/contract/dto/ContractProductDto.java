package kr.or.kosa.cmsplusmain.domain.contract.dto;

import com.querydsl.core.annotations.QueryProjection;

import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;
import kr.or.kosa.cmsplusmain.domain.contract.validator.ContractName;
import kr.or.kosa.cmsplusmain.domain.product.validator.ProductName;
import lombok.Builder;
import lombok.Getter;

public class ContractProductDto {

	@Getter
	public static class Res {
		private final Long contractProductId;
		private final String name;
		private final int price;
		private final int quantity;

		@QueryProjection
		@Builder
		public Res(Long contractProductId, String name, int price, int quantity) {
			this.contractProductId = contractProductId;
			this.name = name;
			this.price = price;
			this.quantity = quantity;
		}
	}

	@Getter
	@Builder
	public static class Req {

		@ProductName
		private final String name;
		private final int price;
		private final int quantity;

		public ContractProduct toEntity(Long contractId) {
			return ContractProduct.builder()
				.contract(Contract.of(contractId))
				.name(name)
				.price(price)
				.quantity(quantity)
				.build();
		}
	}


}
