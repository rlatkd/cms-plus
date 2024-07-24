package kr.or.kosa.cmsplusmain.domain.member.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import kr.or.kosa.cmsplusmain.domain.kafka.MessageSendMethod;
import kr.or.kosa.cmsplusmain.domain.member.entity.Address;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.member.entity.MemberStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {
	/************ 기본정보 ************/
	private Long id;
	private MemberStatus status;
	private String name;
	private String email;
	private String phone;
	private String homePhone;
	private Address address;
	private String memo;
	private LocalDate enrollDate;
	/************ 청구정보 ************/
	private MessageSendMethod invoiceSendMethod;
	private boolean autoInvoiceSend;
	private boolean autoBilling;
	/************ 생성정보 ************/
	private LocalDateTime createdDateTime;
	private LocalDateTime modifiedDateTime;

	public static MemberDto fromEntity(Member member) {
		return MemberDto.builder()
			.id(member.getId())
			.status(member.getStatus())
			.name(member.getName())
			.email(member.getEmail())
			.phone(member.getPhone())
			.homePhone(member.getHomePhone())
			.address(member.getAddress())
			.memo(member.getMemo())
			.enrollDate(member.getEnrollDate())
			.invoiceSendMethod(member.getInvoiceSendMethod())
			.autoInvoiceSend(member.isAutoInvoiceSend())
			.autoBilling(member.isAutoBilling())
			.createdDateTime(member.getCreatedDateTime())
			.modifiedDateTime(member.getModifiedDateTime())
			.build();
	}
}
