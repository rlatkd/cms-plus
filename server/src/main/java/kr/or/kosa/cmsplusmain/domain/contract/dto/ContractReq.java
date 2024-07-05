package kr.or.kosa.cmsplusmain.domain.contract.dto;

import java.util.List;

import kr.or.kosa.cmsplusmain.domain.contract.validator.ContractName;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractReq {
	@ContractName
	private final String contractName;

	private final List<ContractProductDto.Req> contractProducts;
}
