package kr.or.kosa.cmsplusmain.domain.statics.dto.query;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class MemberStatQueryRes {
	private final Long totalMemberCount;
	private final Long newMemberCount;
	private final Long activeMemberCount;

	@QueryProjection
	public MemberStatQueryRes(Long totalMemberCount, Long newMemberCount, Long activeMemberCount) {
		this.totalMemberCount = totalMemberCount;
		this.newMemberCount = newMemberCount;
		this.activeMemberCount = activeMemberCount;
	}
}
