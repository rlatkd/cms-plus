package kr.or.kosa.cmsplusbatch.batch.dto;

import com.querydsl.core.annotations.QueryProjection;
import kr.or.kosa.cmsplusbatch.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusbatch.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusbatch.domain.billing.entity.BillingType;
import kr.or.kosa.cmsplusbatch.domain.contract.entity.Contract;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BillingQueryDto {

    private Long id;
    private Long contractId;
    private String billingType;
    private Integer contractDay;
    private LocalDate billingDate;
    private String billingStatus;
    private String invoiceMessage;
    private LocalDateTime invoiceSendDateTime;
    private LocalDateTime paidDateTime;
    private Long totalBillingProductPrice;
    private String memberName;
    private String memberPhone;
    private List<BillingProductQueryDto> billingProducts;

    @QueryProjection
    public BillingQueryDto(Long id, Long contractId, String billingType, Integer contractDay, LocalDate billingDate,
                           String billingStatus, String invoiceMessage, LocalDateTime invoiceSendDateTime,
                           LocalDateTime paidDateTime, String memberName, String memberPhone) {
        this.id = id;
        this.contractId = contractId;
        this.billingType = billingType;
        this.contractDay = contractDay;
        this.billingDate = billingDate;
        this.billingStatus = billingStatus;
        this.invoiceMessage = invoiceMessage;
        this.invoiceSendDateTime = invoiceSendDateTime;
        this.paidDateTime = paidDateTime;
        this.memberName = memberName;
        this.memberPhone = memberPhone;
    }

    public void setBillingProducts(List<BillingProductQueryDto> billingProducts) {
        this.billingProducts = billingProducts;
    }

    public Billing toEntity(Contract contract) {
        return Billing.builder()
                .id(this.id)
                .contract(contract)
                .billingType(BillingType.valueOf(this.billingType))
                .contractDay(this.contractDay)
                .billingDate(this.billingDate)
                .billingStatus(BillingStatus.valueOf(this.billingStatus))
                .invoiceMessage(this.invoiceMessage)
                .invoiceSendDateTime(this.invoiceSendDateTime)
                .paidDateTime(this.paidDateTime)
                .build();
    }

    public void setInvoiceSent() {
        this.billingStatus = "WAITING_PAYMENT";
        this.invoiceSendDateTime = LocalDateTime.now();
    }

    public String getInvoiceName() {
        String year = Integer.toString(billingDate.getYear());
        String month = Integer.toString(billingDate.getMonthValue());
        return "%s년 %s월 청구서".formatted(year, month);
    }
}
