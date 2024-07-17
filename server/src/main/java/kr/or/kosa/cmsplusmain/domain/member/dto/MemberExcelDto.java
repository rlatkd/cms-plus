package kr.or.kosa.cmsplusmain.domain.member.dto;

import java.time.LocalDate;

import kr.or.kosa.cmsplusmain.domain.excel.ExcelColumn;
import kr.or.kosa.cmsplusmain.domain.member.entity.Address;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberExcelDto {
	@ExcelColumn(headerName = "회원명")
	private String memberName;
	@ExcelColumn(headerName = "휴대전화")
	private String memberPhone;
	@ExcelColumn(headerName = "우편번호")
	private String zipcode;
	@ExcelColumn(headerName = "주소")
	private String address;
	@ExcelColumn(headerName = "상세주소")
	private String addressDetail;
	@ExcelColumn(headerName = "등록일")
	private LocalDate memberEnrollDate;
	@ExcelColumn(headerName = "유선전화")
	private String memberHomePhone;
	@ExcelColumn(headerName = "이메일")
	private String memberEmail;

	public Member toEntity(Vendor vendor) {
		Address mAddress = new Address(zipcode, address, addressDetail);
		return Member.builder()
			.vendor(vendor)
			.name(memberName)
			.phone(memberPhone)
			.address(mAddress)
			.enrollDate(memberEnrollDate)
			.homePhone(memberHomePhone)
			.email(memberEmail)
			.build();
	}
}
