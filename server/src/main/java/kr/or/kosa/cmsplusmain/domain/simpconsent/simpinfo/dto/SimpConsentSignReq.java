package kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SimpConsentSignReq {
	@NotNull
	private Long contractId;
	@NotBlank
	private String signImgUrl;
}
