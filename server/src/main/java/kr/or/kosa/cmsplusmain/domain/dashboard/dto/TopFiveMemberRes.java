package kr.or.kosa.cmsplusmain.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopFiveMemberRes {
	private final Long memberId;
	private final String memberName;
	private final Long totalContractPrice;
	private final int contractCount;
}
