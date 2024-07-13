package kr.or.kosa.cmsplusmain.domain.billing.entity;

import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEnum;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BillingStatus implements BaseEnum {
	CREATED("생성"), WAITING_PAYMENT("수납 대기중"), PAID("완납"), NON_PAID("미납");

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
