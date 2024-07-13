package kr.or.kosa.cmsplusmain.domain.contract.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.contract.validator.ContractDay;
import kr.or.kosa.cmsplusmain.domain.contract.validator.ContractName;
import lombok.Getter;

@Getter
public class ContractCreateReq {
	@ContractName @NotNull
	private String contractName;						// 계약명
	@NotNull
	private List<ContractProductReq> contractProducts;	// 계약상품 목록
	@NotNull
	private LocalDate contractStartDate;				// 계약 시작일
	@NotNull
	private LocalDate contractEndDate;					// 계약 종료일
	@ContractDay @NotNull
	private Integer contractDay;						// 계약 약정일
}
