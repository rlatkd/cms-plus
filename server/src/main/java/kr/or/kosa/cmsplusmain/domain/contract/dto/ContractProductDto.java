package kr.or.kosa.cmsplusmain.domain.contract.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ContractProductDto {

	private final Long contractProductId;
	private final String name;
	private final int price;
	private final int quantity;

	@QueryProjection
	@Builder
	public ContractProductDto(Long contractProductId, String name, int price, int quantity) {
		this.contractProductId = contractProductId;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
}
