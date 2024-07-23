package kr.or.kosa.cmsplusmain.domain.billing.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.billing.validator.InvoiceMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BillingUpdateReq {
	@InvoiceMessage
	private String invoiceMessage;
	@NotNull
	private LocalDate billingDate;
	@NotNull
	private List<@Valid BillingProductReq> billingProducts;
}
