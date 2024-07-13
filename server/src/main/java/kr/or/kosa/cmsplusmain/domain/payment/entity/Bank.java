package kr.or.kosa.cmsplusmain.domain.payment.entity;

import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Bank implements BaseEnum {
	SINHAN("088", "신한은행"), KOOKMIN("004", "국민은행");

	private final String code;
	private final String title;

	public static Bank fromCode(String code) {
		if (code == null || code.isEmpty()) {
			return null;
		}

		for (Bank bank : values()) {
			if (bank.code.equals(code)) {
				return bank;
			}
		}

		return null;
	}
}
