package kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.dto;

import java.util.List;

import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractDto;
import kr.or.kosa.cmsplusmain.domain.contract.dto.response.ContractProductRes;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberDto;
import kr.or.kosa.cmsplusmain.domain.payment.dto.method.PaymentMethodInfoRes;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.PaymentTypeInfoRes;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SimpConsentInfoRes {
	private MemberDto member;
	private ContractDto contract;
	private List<ContractProductRes> contractProducts;
	private PaymentTypeInfoRes paymentTypeInfo;
	private PaymentMethodInfoRes paymentMethodInfo;
}
