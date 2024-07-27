package kr.or.kosa.cmsplusmain.domain.statics.dto.query;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class MemberStatQueryRes {
	private final long totalMemberCount;
	private final long newMemberCount;
	private final long activeMemberCount;

	@QueryProjection
	public MemberStatQueryRes(long totalMemberCount, long newMemberCount, long activeMemberCount) {
		this.totalMemberCount = totalMemberCount;
		this.newMemberCount = newMemberCount;
		this.activeMemberCount = activeMemberCount;
	}
}
