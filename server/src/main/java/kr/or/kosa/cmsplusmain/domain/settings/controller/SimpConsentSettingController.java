package kr.or.kosa.cmsplusmain.domain.settings.controller;

import kr.or.kosa.cmsplusmain.domain.base.security.VendorId;
import kr.or.kosa.cmsplusmain.domain.settings.dto.AvailableOptionsDto;
import kr.or.kosa.cmsplusmain.domain.settings.dto.SimpConsentSettingDto;
import kr.or.kosa.cmsplusmain.domain.settings.service.SimpConsentSettingService;
import kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.service.SimpleConsentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public SimpConsentSettingDto getSetting(@VendorId Long vendorId ) {
        return simpConsentSettingService.getSetting(vendorId);
    }

    /**
     * 고객 간편동의 설정 세팅 수정
     * */
    @PutMapping
    public SimpConsentSettingDto updateSetting(@VendorId Long vendorId ,@RequestBody SimpConsentSettingDto dto) {
        return simpConsentSettingService.updateSetting(vendorId, dto);
    }

    /**
     * 간편동의 상품, 결제수단 리스트 조회
     * */
    @GetMapping("/available-options/{vendorId}")
    public AvailableOptionsDto getAvailableOptions(@PathVariable Long vendorId) {
        return  simpConsentSettingService.getAvailableOptions(vendorId);
    }

    /**
     * 간편동의 요청 링크 발송
     * */
    @GetMapping("/send/{contractId}")
    public void sendSimpleConsentUrl(@VendorId Long vendorId, @PathVariable Long contractId) {
        simpleConsentService.sendReqUrl(vendorId, contractId);
    }
}