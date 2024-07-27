package kr.or.kosa.cmsplusmain.domain.vendor.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessTokenRes {
	private final String role;
	private final String name;
	private final String username;
	private final Long vendorId;
	private final String accessToken;
}
