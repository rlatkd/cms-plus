package kr.or.kosa.cmsplusmain.domain.contract.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class ContractReq {
	private String contractName;
	private List<ContractProductReq> contractProducts;
}
