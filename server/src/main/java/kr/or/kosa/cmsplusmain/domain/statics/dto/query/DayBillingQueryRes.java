package kr.or.kosa.cmsplusmain.domain.statics.dto.query;

import java.time.LocalDate;

import com.querydsl.core.annotations.QueryProjection;

import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import lombok.Getter;

@Getter
public class DayBillingQueryRes {
	private final LocalDate billingDate;
	private final BillingStatus billingStatus;
	private final Integer billingCount;
	private final Long billingPrice;

	@QueryProjection
	public DayBillingQueryRes(LocalDate billingDate, BillingStatus billingStatus, Integer billingCount,
		Long billingPrice) {
		this.billingDate = billingDate;
		this.billingStatus = billingStatus;
		this.billingCount = billingCount;
		this.billingPrice = billingPrice;
	}
}
