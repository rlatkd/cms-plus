package kr.or.kosa.cmsplusmain.domain.contract.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.messaging.MessageSendMethod;
import kr.or.kosa.cmsplusmain.domain.payment.dto.PaymentDto;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ContractDetail {

	private Long contractId;
	private String contractName;

	private LocalDateTime createdDateTime;
	private LocalDateTime modifiedDateTime;

	private List<ContractProductDto.Res> contractProducts;

	private int contractDay;

	private LocalDate contractStartDate;
	private LocalDate contractEndDate;

	// 결제정보
	private PaymentDto payment;

	// 회원 청구정보
	private boolean autoBilling;
	private boolean autoInvoiceSend;
	private String invoiceSendMethod;

	@Builder
	public ContractDetail(Long contractId, String contractName, LocalDateTime createdDateTime,
		LocalDateTime modifiedDateTime, List<ContractProduct> contractProducts, int contractDay,
		LocalDate contractStartDate, LocalDate contractEndDate, Payment payment, boolean autoBilling,
		boolean autoInvoiceSend, MessageSendMethod invoiceSendMethod) {
		this.contractId = contractId;
		this.contractName = contractName;
		this.createdDateTime = createdDateTime;
		this.modifiedDateTime = modifiedDateTime;
		this.contractProducts = (contractProducts != null) ?
			contractProducts.stream().map(ContractProductDto.Res::fromEntity).toList() : Collections.emptyList();
		this.contractDay = contractDay;
		this.contractStartDate = contractStartDate;
		this.contractEndDate = contractEndDate;
		this.payment = PaymentDto.fromEntity(payment);
		this.autoBilling = autoBilling;
		this.autoInvoiceSend = autoInvoiceSend;
		this.invoiceSendMethod = (invoiceSendMethod != null) ? invoiceSendMethod.getTitle() : null;
	}

	public static ContractDetail fromEntity(Contract contract) {
		if (contract == null) {
			return null;
		}
		Member member = contract.getMember();
		return ContractDetail.builder()
			.contractId(contract.getId())
			.contractName(contract.getName())
			.createdDateTime(contract.getCreatedDateTime())
			.modifiedDateTime(contract.getModifiedDateTime())
			.contractProducts(contract.getContractProducts())
			.contractDay(contract.getContractDay())
			.contractStartDate(contract.getContractStartDate())
			.contractEndDate(contract.getContractEndDate())
			.payment(contract.getPayment())
			.autoBilling(member.isAutoBilling())
			.autoInvoiceSend(member.isAutoInvoiceSend())
			.invoiceSendMethod(member.getInvoiceSendMethod())
			.build();
	}
}
