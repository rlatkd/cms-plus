package kr.or.kosa.cmsplusmain.domain.contract.dto;

import java.time.LocalDate;
import java.util.List;

import kr.or.kosa.cmsplusmain.domain.contract.validator.ContractName;
import kr.or.kosa.cmsplusmain.domain.payment.dto.CMSInfo;
import kr.or.kosa.cmsplusmain.domain.payment.dto.CardInfo;
import kr.or.kosa.cmsplusmain.domain.payment.dto.VirtualAccountInfo;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentMethod;
import lombok.Getter;

@Getter
public class ContractCreateReq {
	@ContractName
	private String contractName;
	private List<ContractProductDto.Req> contractProducts;

	// 결제 정보
	private String paymentType;
	private Integer contractDay;
	private LocalDate startDate;
	private LocalDate endDate;

	// 회원설정인 경우 null
	private String paymentMethod;

	// 자동결제
	private CardInfo cardInfo;
	private CMSInfo cmsInfo;

	// 납부자결제
	private List<PaymentMethod> buyerPaymentMethods;

	// 가상계좌
	private VirtualAccountInfo virtualAccountInfo;

	// public Contract toEntity(Vendor vendor, Member member) {
	// 	Payment payment = Payment
	// 	return Contract.builder()
	// 		.vendor(vendor)
	// 		.member(member)
	// 		.name(contractName)
	// 		.contractDay(contractDay)
	// 		.
	// }
}
