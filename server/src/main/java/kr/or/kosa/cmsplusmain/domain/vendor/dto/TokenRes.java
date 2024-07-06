package kr.or.kosa.cmsplusmain.domain.vendor.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenRes {
    private final String accessToken;
    private final String role;
}
