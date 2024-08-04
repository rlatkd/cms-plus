package kr.or.kosa.cmsplusmain.domain.dashboard.dto.query;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class BillingStatQueryRes {
	private final long totalBillingPrice;
	private final long totalPaidPrice;
	private final long totalNotPaidPrice;

	@QueryProjection
	public BillingStatQueryRes(long totalBillingPrice, long totalPaidPrice, long totalNotPaidPrice) {
		this.totalBillingPrice = totalBillingPrice;
		this.totalPaidPrice = totalPaidPrice;
		this.totalNotPaidPrice = totalNotPaidPrice;
	}
}
