package kr.or.kosa.cmsplusmain.domain.payment.entity;

import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConsentStatus implements BaseEnum {
	NONE("미동의"), WAIT("대기중"), ACCEPT("승인"), NOT_USED("불필요");

	private final String title;

	@Override
	public String getCode() {
		return name();
	}

	@Override
	public String getTitle() {
		return title;
	}
}
