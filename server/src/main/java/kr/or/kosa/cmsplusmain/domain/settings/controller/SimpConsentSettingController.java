package kr.or.kosa.cmsplusmain.domain.settings.controller;

import kr.or.kosa.cmsplusmain.domain.base.security.VendorId;
import kr.or.kosa.cmsplusmain.domain.settings.dto.AvailableOptionsDto;
import kr.or.kosa.cmsplusmain.domain.settings.dto.SimpConsentSettingDto;
import kr.or.kosa.cmsplusmain.domain.settings.service.SimpConsentSettingService;
import kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.service.SimpleConsentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendor/simple-consent")
@RequiredArgsConstructor
public class SimpConsentSettingController {

    private final SimpConsentSettingService simpConsentSettingService;
    private final SimpleConsentService simpleConsentService;

    /**
     * 고객 간편동의 설정 세팅 조회
     * */
    @GetMapping
    public ResponseEntity<SimpConsentSettingDto> getSetting(@VendorId Long vendorId ) {
//        Long vendorId = 1L;
        return ResponseEntity.ok(simpConsentSettingService.getSetting(vendorId));
    }

    /**
     * 고객 간편동의 설정 세팅 수정
     * */
    @PutMapping
    public ResponseEntity<SimpConsentSettingDto> updateSetting(@VendorId Long vendorId ,@RequestBody SimpConsentSettingDto dto) {
//        Long vendorId = 1L;
        return ResponseEntity.ok(simpConsentSettingService.updateSetting(vendorId, dto));
    }

    /**
     * 간편동의 상품, 결제수단 리스트 조회
     * */
    @GetMapping("/available-options")
    public ResponseEntity<?> getAvailableOptions(@VendorId Long vendorId) {
//        Long vendorId = 1L;
        AvailableOptionsDto options = simpConsentSettingService.getAvailableOptions(vendorId);
        return ResponseEntity.ok(options);
    }

    /**
     * 간편동의 요청 링크 발송
     * */
    @GetMapping("/send/{contractId}")
    public void sendSimpleConsentUrl(@VendorId Long vendorId, @PathVariable Long contractId) {
        simpleConsentService.sendReqUrl(vendorId, contractId);
    }
}