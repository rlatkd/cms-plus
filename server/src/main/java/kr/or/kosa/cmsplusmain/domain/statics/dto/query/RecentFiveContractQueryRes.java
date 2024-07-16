package kr.or.kosa.cmsplusmain.domain.statics.dto.query;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class RecentFiveContractQueryRes {
	private final LocalDateTime createDateTime;
	private final String memberName;
	private final Long totalContractPrice;
	private final LocalDate contractStartDate;
	private final LocalDate contractEndDate;

	@QueryProjection
	public RecentFiveContractQueryRes(LocalDateTime createDateTime, String memberName, Long totalContractPrice,
		LocalDate contractStartDate, LocalDate contractEndDate) {
		this.createDateTime = createDateTime;
		this.memberName = memberName;
		this.totalContractPrice = totalContractPrice;
		this.contractStartDate = contractStartDate;
		this.contractEndDate = contractEndDate;
	}
}
