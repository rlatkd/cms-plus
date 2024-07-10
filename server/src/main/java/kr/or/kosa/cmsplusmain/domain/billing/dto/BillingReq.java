package kr.or.kosa.cmsplusmain.domain.billing.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BillingReq {
	@NotNull
	private BillingType billingType;
	@NotNull
	private LocalDate billingDate;
	@NotNull
	private Long contractId;
	@NotNull
	private List<BillingProductReq> billingProducts;
}
