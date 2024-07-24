package kr.or.kosa.cmsplusmain.domain.base.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ButtonStateRes {
	private String label;
	private String reason;
	private Boolean isEnabled;
	private Boolean isCancel;
}
