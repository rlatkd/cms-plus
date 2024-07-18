package kr.or.kosa.cmsplusmain.domain.contract.dto;

import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.contract.validator.ContractName;
import lombok.Getter;

import java.util.List;

@Getter
public class ContractUpdateReq {

    @ContractName
    @NotNull
    private String contractName;						// 계약명

    @NotNull
    private List<ContractProductReq> contractProducts;	// 계약상품 목록
}
