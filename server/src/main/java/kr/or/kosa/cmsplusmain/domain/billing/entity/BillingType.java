package kr.or.kosa.cmsplusmain.domain.billing.entity;

import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEnum;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BillingType implements BaseEnum {
	REGULAR("정기청구"), IRREGULAR("추가청구");

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
