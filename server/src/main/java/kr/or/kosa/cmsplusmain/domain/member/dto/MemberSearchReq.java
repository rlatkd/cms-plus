package kr.or.kosa.cmsplusmain.domain.member.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberSearchReq {

	/***** 포함 *****/
	private String memberName;          // 회원 이름
	private String memberPhone;         // 회원 휴대전화
	private String memberEmail;         // 회원 이메일
	private LocalDate memberEnrollDate; // 회원 등록일

	/***** 이하 *****/
	private Long contractPrice;         // 총 계약 금액
	private Integer contractCount;      // 계약 개수
}
