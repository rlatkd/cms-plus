package kr.or.kosa.cmsplusmain.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.base.validator.HomePhone;
import kr.or.kosa.cmsplusmain.domain.base.validator.Memo;
import kr.or.kosa.cmsplusmain.domain.base.validator.PersonName;
import kr.or.kosa.cmsplusmain.domain.base.validator.Phone;
import kr.or.kosa.cmsplusmain.domain.member.entity.Address;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberUpdateReq {

    @NotBlank
    @PersonName
    private String memberName ; // 회원 이름

    @NotBlank
    @Phone
    String memberPhone ; // 회원 휴대전화 번호

    @NotNull
    private LocalDate memberEnrollDate; // 회원 등록 날짜

    @HomePhone
    private String memberHomePhone; // 회원 유선전화 번호

    @NotBlank
    @Email
    private String memberEmail; // 회원 이메일

    private Address memberAddress; // 회원 주소

    @Memo
    private String memberMemo; // 회원 메모

}
