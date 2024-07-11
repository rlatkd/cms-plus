package kr.or.kosa.cmsplusmain.domain.billing.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingType;
import lombok.Getter;

@Getter
public class BillingUpdateReq {
	private String invoiceMemo;
	@NotNull
	private LocalDate billingDate;
	@NotNull
	private List<BillingProductReq> billingProducts;
}
