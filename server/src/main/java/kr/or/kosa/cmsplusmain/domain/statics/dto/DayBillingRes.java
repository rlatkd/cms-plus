package kr.or.kosa.cmsplusmain.domain.statics.dto;

import java.time.LocalDate;
import java.util.List;

import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItemRes;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DayBillingRes {
	private final LocalDate billingDate;
	private final Long totalDayBillingPrice;
	private final Integer totalDayBillingCount;
}
