

package kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.controller;

import kr.or.kosa.cmsplusmain.domain.base.security.VendorId;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberDetail;
import kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.dto.SimpleConsentRequestDTO;
import kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.service.SimpleConsentService;
import kr.or.kosa.cmsplusmain.domain.vendor.dto.VendorUserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/simple-consent")
@RequiredArgsConstructor
public class SimpleConsentController {

    private final SimpleConsentService simpleConsentService;
    private static final Logger logger = LoggerFactory.getLogger(SimpleConsentController.class);

    /**
     * 고객 간편동의 (회원, 비회원) 등록
     * */
    @PostMapping("/{vendorId}")
    public ResponseEntity<MemberDetail> processSimpleConsent(@PathVariable Long vendorId , @RequestBody SimpleConsentRequestDTO simpleConsentRequestDTO) {
//        Long vendorId = 1L;
        //String vendorName = userDetails.getName();

        logger.info("Processing simple consent for vendor: (ID: {})", vendorId);
        MemberDetail memberDetail = simpleConsentService.processSimpleConsent(
                vendorId,
                simpleConsentRequestDTO.getMemberDTO(),
                simpleConsentRequestDTO.getPaymentDTO(),
                simpleConsentRequestDTO.getContractDTO()
        );
        logger.info("Simple consent processed successfully for vendor: (ID: {})", vendorId);
        return ResponseEntity.ok(memberDetail);
    }

}
