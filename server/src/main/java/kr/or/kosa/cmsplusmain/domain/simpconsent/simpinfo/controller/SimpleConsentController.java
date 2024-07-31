

package kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.kosa.cmsplusmain.domain.base.security.VendorId;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberDetail;
import kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.dto.SimpConsentInfoRes;
import kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.dto.SimpConsentSignReq;
import kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.dto.SimpleConsentRequestDTO;
import kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.service.SimpleConsentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/simple-consent")
@RequiredArgsConstructor
@Slf4j
public class SimpleConsentController {

    private final SimpleConsentService simpleConsentService;

    @PostMapping
    public ResponseEntity<MemberDetail> processSimpleConsent(@VendorId Long vendorId , @RequestBody SimpleConsentRequestDTO simpleConsentRequestDTO) {
//        Long vendorId = 1L;
        //String vendorName = userDetails.getName();

        log.info("Processing simple consent for vendor: (ID: {})", vendorId);
        MemberDetail memberDetail = simpleConsentService.processSimpleConsent(
                vendorId,
                simpleConsentRequestDTO.getMemberDTO(),
                simpleConsentRequestDTO.getPaymentDTO(),
                simpleConsentRequestDTO.getContractDTO()
        );
        log.info("Simple consent processed successfully for vendor: (ID: {})", vendorId);
        return ResponseEntity.ok(memberDetail);
    }

    @GetMapping
    public SimpConsentInfoRes getSimpleConsentInfo(
        @RequestParam(name = "vendor") Long vendorId,
        @RequestParam(name = "contract") Long contractId) {
        return simpleConsentService.getSimpleConsentInfo(vendorId, contractId);
    }

    @PostMapping("/{vendorId}}")
    public void uploadSignUrl(@PathVariable Long vendorId, @RequestBody SimpConsentSignReq simpConsentSignReq) {
        simpleConsentService.setSigned(vendorId, simpConsentSignReq);
    }

}
