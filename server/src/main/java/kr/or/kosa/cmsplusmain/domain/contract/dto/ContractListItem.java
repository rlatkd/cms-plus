package kr.or.kosa.cmsplusmain.domain.contract.dto;

import java.util.Collections;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;

import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ContractListItem {
	private final Long contractId;
	private final String memberName;
	private final String memberPhone;
	private final Integer contractDay;
	private final List<ContractProductDto.Res> contractProducts;
	private final Long contractPrice;
	private final String contractStatus;
	private final String consentStatus;

	@Builder
	public ContractListItem(
		Long contractId,
		String memberName, String memberPhone,
		Integer contractDay, List<ContractProduct> contractProducts,
		Long contractPrice, ContractStatus contractStatus, ConsentStatus consentStatus)
	{
		this.contractId = contractId;
		this.memberName = memberName;
		this.memberPhone = memberPhone;
		this.contractDay = contractDay;
		this.contractProducts = (contractProducts != null) ?
			contractProducts.stream()
				.map(ContractProductDto.Res::fromEntity)
				.toList() : Collections.emptyList();
		this.contractPrice = contractPrice;
		this.contractStatus = (contractStatus != null) ? contractStatus.getTitle() : null;
		this.consentStatus = (consentStatus != null) ? consentStatus.getTitle() : null;
	}
}
