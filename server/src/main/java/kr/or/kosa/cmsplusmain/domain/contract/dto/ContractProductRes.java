package kr.or.kosa.cmsplusmain.domain.contract.dto;

import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingProductRes;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingProduct;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractProductRes {

	private Long id;
	private Long contractId;
	private String name;
	private	Integer price;
	private Integer quantity;

	public static ContractProductRes fromEntity(ContractProduct contractProduct) {
		return ContractProductRes.builder()
			.id(contractProduct.getId())
			.contractId(contractProduct.getContract().getId())

			// 주의
			// 청구상품 과 청구 join 조회 필요
			.name(contractProduct.getProduct().getName())

			.price(contractProduct.getPrice())
			.quantity(contractProduct.getQuantity())
			.build();
	}
}
