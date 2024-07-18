package kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SimpleConsentRequestDTO {
    private SimpleConsentMemberDTO memberDTO;
    private SimpleConsentPaymentDTO paymentDTO;
    private SimpleConsentContractDTO contractDTO;
}