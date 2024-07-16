package kr.or.kosa.cmsplusmain.domain.statics.dto.query;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class ContractStatQueryRes {
	private final Long totalContractCount;
	private final Long newContractCount;
	private final Long expectedToExpiredCount;

	@QueryProjection
	public ContractStatQueryRes(Long totalContractCount, Long newContractCount, Long expectedToExpiredCount) {
		this.totalContractCount = totalContractCount;
		this.newContractCount = newContractCount;
		this.expectedToExpiredCount = expectedToExpiredCount;
	}
}
