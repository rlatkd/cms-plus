package kr.or.kosa.cmsplusbatch.domain.member.entity;

import lombok.Getter;

@Getter
public enum MemberStatus {
	ENABLED("활성화"), DISABLED("비활성화");

	private final String title;

	MemberStatus(String title) {
		this.title = title;
	}
}
