package kr.or.kosa.cmsplusmain.domain.statics.dto.query;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class BillingStatQueryRes {
	private final Long totalBillingPrice;
	private final Long totalPaidPrice;
	private final Long totalNotPaidPrice;

	@QueryProjection
	public BillingStatQueryRes(Long totalBillingPrice, Long totalPaidPrice, Long totalNotPaidPrice) {
		this.totalBillingPrice = totalBillingPrice;
		this.totalPaidPrice = totalPaidPrice;
		this.totalNotPaidPrice = totalNotPaidPrice;
	}
}
