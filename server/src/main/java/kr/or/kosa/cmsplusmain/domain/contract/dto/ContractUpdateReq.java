package kr.or.kosa.cmsplusmain.domain.contract.dto;

import java.util.List;

import kr.or.kosa.cmsplusmain.domain.contract.validator.ContractName;
import lombok.Getter;

@Getter
public class ContractUpdateReq {
	@ContractName
	private String contractName;
	private List<ContractProductDto.Req> contractProducts;
}
