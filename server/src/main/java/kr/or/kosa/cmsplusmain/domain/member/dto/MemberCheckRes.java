package kr.or.kosa.cmsplusmain.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberCheckRes {
    private final boolean isPhoneExist;
    private final boolean isEmailExist;
}
