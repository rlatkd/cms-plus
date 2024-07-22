package kr.or.kosa.cmsplusmain.domain.statics.dto.query;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class TopFiveMemberQueryRes {
	private final Long memberId;
	private final String memberName;
	private final Long totalContractPrice;
	private final int contractCount;

	@QueryProjection
	public TopFiveMemberQueryRes(Long memberId, String memberName, Long totalContractPrice, int contractCount) {
		this.memberId = memberId;
		this.memberName = memberName;
		this.totalContractPrice = totalContractPrice;
		this.contractCount = contractCount;
	}
}
