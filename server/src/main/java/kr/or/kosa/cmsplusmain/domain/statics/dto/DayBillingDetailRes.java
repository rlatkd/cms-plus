package kr.or.kosa.cmsplusmain.domain.statics.dto;

import java.util.List;
import java.util.Map;

import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingProductRes;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DayBillingDetailRes {
	private final Long totalBillingPrice;
	private final Long createdBillingPrice;
	private final Long paidBillingPrice;
	private final Long waitBillingPrice;
	private final Long nonPaidBillingPrice;

	private final Integer totalBillingAmount;
	private final Integer createdBillingAmount;
	private final Integer waitBillingAmount;
	private final Integer paidBillingAmount;
	private final Integer nonPaidBillingAmount;

	private List<BillingProductRes> billingProducts;
}
