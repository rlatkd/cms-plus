package kr.or.kosa.cmsplusmain.domain.statics.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class RecentFiveContractRes {
	private final LocalDate contractStartDate;
	private final String memberName;
	private final Long totalContractPrice;
	private final int contractMonth;

	public RecentFiveContractRes(LocalDate contractStartDate, String memberName, Long totalContractPrice, int contractMonth) {
		this.contractStartDate = contractStartDate;
		this.memberName = memberName;
		this.totalContractPrice = totalContractPrice;
		this.contractMonth = contractMonth;
	}
}
