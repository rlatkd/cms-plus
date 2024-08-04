package kr.or.kosa.cmsplusmain.domain.dashboard.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopInfoRes {
	private final List<TopFiveMemberRes> topFiveMemberRes;
	private final List<RecentFiveContractRes> recentFiveContracts;
}
