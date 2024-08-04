package kr.or.kosa.cmsplusmain.domain.dashboard.dto.query;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class ContractStatQueryRes {
	private final long totalContractCount;
	private final long newContractCount;
	private final long expectedToExpiredCount;

	@QueryProjection
	public ContractStatQueryRes(long totalContractCount, long newContractCount, long expectedToExpiredCount) {
		this.totalContractCount = totalContractCount;
		this.newContractCount = newContractCount;
		this.expectedToExpiredCount = expectedToExpiredCount;
	}
}
