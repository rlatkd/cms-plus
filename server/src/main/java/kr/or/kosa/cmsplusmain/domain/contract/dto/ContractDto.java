package kr.or.kosa.cmsplusmain.domain.contract.dto;

import java.time.LocalDate;

import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContractDto {
	private Long id;
	private String contractName;
	private Integer contractDay;
	private LocalDate contractStartDate;
	private LocalDate contractEndDate;
	private ContractStatus contractStatus;
	private Long contractPrice;
	private Integer contractProductCnt;

	public static ContractDto fromEntity(Contract contract) {
		return new ContractDto(
			contract.getId(), contract.getContractName(), contract.getContractDay(),
			contract.getContractStartDate(), contract.getContractEndDate(), contract.getContractStatus(),
			contract.getContractPrice(), contract.getContractProductCnt());
	}
}
