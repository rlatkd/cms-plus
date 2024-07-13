package kr.or.kosa.cmsplusmain.domain.contract.dto;

import java.time.LocalDateTime;
import java.util.List;

import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.payment.dto.method.PaymentMethodInfoRes;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.PaymentTypeInfoRes;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethodInfo;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentTypeInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractDetailRes {

	private final Long contractId;                                	// 계약 ID
	private final String contractName;                              // 계약이름
	private final LocalDateTime createdDateTime;                	// 계약 등록일시
	private final LocalDateTime modifiedDateTime;                	// 계약 변경일시
	private final List<ContractProductRes> contractProducts;    	// 계약 상품 목록
	private final Long contractPrice;                            	// 계약금액

	private final PaymentTypeInfoRes paymentTypeInfo;               // 결제방식
	private final PaymentMethodInfoRes paymentMethodInfo;			// 결제수단

	public static ContractDetailRes fromEntity(
		Contract contract,
		PaymentTypeInfoRes paymentTypeInfoRes, PaymentMethodInfoRes paymentMethodInfoRes) {

		// NOT NULL
		final List<ContractProductRes> contractProductResList = contract.getContractProducts()
			.stream()
			.map(ContractProductRes::fromEntity)
			.toList();
		
		return ContractDetailRes.builder()
			.contractId(contract.getId())
			.contractName(contract.getContractName())
			.createdDateTime(contract.getCreatedDateTime())
			.modifiedDateTime(contract.getModifiedDateTime())
			.contractProducts(contractProductResList)
			.contractPrice(contract.getContractPrice())
			.paymentTypeInfo(paymentTypeInfoRes)
			.paymentMethodInfo(paymentMethodInfoRes)
			.build();
	}
}
