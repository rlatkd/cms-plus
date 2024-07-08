package kr.or.kosa.cmsplusmain.domain.contract.dto;

import java.time.LocalDate;
import java.util.List;

import kr.or.kosa.cmsplusmain.domain.contract.validator.ContractName;
import kr.or.kosa.cmsplusmain.domain.payment.dto.CMSInfo;
import kr.or.kosa.cmsplusmain.domain.payment.dto.CardInfo;
import kr.or.kosa.cmsplusmain.domain.payment.dto.PaymentDto;
import kr.or.kosa.cmsplusmain.domain.payment.dto.VirtualAccountInfo;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentMethod;
import lombok.Getter;

@Getter
public class ContractCreateReq {
	@ContractName
	private String contractName;
	private List<ContractProductDto.Req> contractProducts;

	// 결제 정보
	private PaymentDto payment;
}
