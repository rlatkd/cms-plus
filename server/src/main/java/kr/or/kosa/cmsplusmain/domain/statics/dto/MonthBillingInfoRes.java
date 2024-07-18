package kr.or.kosa.cmsplusmain.domain.statics.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonthBillingInfoRes {
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

	private final List<DayBillingRes> dayBillingRes;
}
