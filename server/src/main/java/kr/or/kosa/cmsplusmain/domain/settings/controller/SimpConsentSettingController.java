package kr.or.kosa.cmsplusmain.domain.settings.controller;

import kr.or.kosa.cmsplusmain.domain.settings.dto.AvailableOptionsDto;
import kr.or.kosa.cmsplusmain.domain.settings.dto.SimpConsentSettingDto;
import kr.or.kosa.cmsplusmain.domain.settings.service.SimpConsentSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendor/simpconsent-settings")
@RequiredArgsConstructor
public class SimpConsentSettingController {

    private final SimpConsentSettingService simpConsentSettingService;

    @PostMapping
    public ResponseEntity<SimpConsentSettingDto> createSetting(@RequestParam String username) {
        return ResponseEntity.ok(simpConsentSettingService.createSetting(username));
    }

    @GetMapping("/{username}")
    public ResponseEntity<SimpConsentSettingDto> getSetting(@PathVariable String username) {
        return ResponseEntity.ok(simpConsentSettingService.getSetting(username));
    }

    @PutMapping("/{username}")
    public ResponseEntity<SimpConsentSettingDto> updateSetting(@PathVariable String username, @RequestBody SimpConsentSettingDto dto) {
        return ResponseEntity.ok(simpConsentSettingService.updateSetting(username, dto));
    }

    @GetMapping("/available-options")
    public ResponseEntity<?> getAvailableOptions(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        AvailableOptionsDto options = simpConsentSettingService.getAvailableOptions(username);
        return ResponseEntity.ok(options);
    }

}