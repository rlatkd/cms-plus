package kr.or.kosa.cmsplusmain.domain.statics.dto.query;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class TopFiveMemberQueryRes {
	private final String memberName;
	private final Long totalContractPrice;
	private final int contractCount;

	@QueryProjection
	public TopFiveMemberQueryRes(String memberName, Long totalContractPrice, int contractCount) {
		this.memberName = memberName;
		this.totalContractPrice = totalContractPrice;
		this.contractCount = contractCount;
	}
}
