package kr.or.kosa.cmsplusmain.domain.billing.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BillingDto {
	/************ 기본정보 ************/
	private Long billingId;
	private BillingType billingType;
	private BillingStatus billingStatus;
	private LocalDate billingDate;
	private LocalDateTime paidDateTime;
	private Integer contractDay;

	/************ 청구서정보 ************/
	private String invoiceName;
	private String invoiceMessage;
	private LocalDateTime invoiceSendDateTime;

	/************ 생성정보 ************/
	private LocalDateTime createdDateTime;
	private LocalDateTime modifiedDateTime;

	public static BillingDto fromEntity(Billing billing) {
		return BillingDto.builder()
			.billingId(billing.getId())
			.invoiceName(billing.getInvoiceName())
			.billingType(billing.getBillingType())
			.billingStatus(billing.getBillingStatus())
			.billingDate(billing.getBillingDate())
			.invoiceSendDateTime(billing.getInvoiceSendDateTime())
			.paidDateTime(billing.getPaidDateTime())
			.contractDay(billing.getContractDay())
			.invoiceMessage(billing.getInvoiceMessage())
			.createdDateTime(billing.getCreatedDateTime())
			.modifiedDateTime(billing.getModifiedDateTime())
			.build();
	}
}
