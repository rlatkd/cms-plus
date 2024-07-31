package kr.or.kosa.cmsplusmain.domain.dashboard.dto;

import java.time.LocalDate;
import java.util.Map;

import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class DayBillingRes {
	private final LocalDate billingDate;
	private final Long totalDayBillingPrice;
	private final Integer totalDayBillingCount;
	private final Map<BillingStatus, Integer> statusCounts;
}
