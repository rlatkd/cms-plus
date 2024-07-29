package kr.or.kosa.cmsplusbatch.domain.billing.entity;

import kr.or.kosa.cmsplusbatch.domain.base.entity.BaseEnum;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BillingStatus implements BaseEnum {
	CREATED("생성"), WAITING_PAYMENT("수납대기"), PAID("완납"), NON_PAID("미납");

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
