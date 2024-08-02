package kr.or.kosa.cmsplusmain.domain.statics.controller;

import kr.or.kosa.cmsplusmain.domain.base.security.VendorId;
import kr.or.kosa.cmsplusmain.domain.statics.dto.MemberContractStatisticDto;
import kr.or.kosa.cmsplusmain.domain.statics.service.MemberContractStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class MemberContractStatisticController {

    private final MemberContractStatisticService memberContractStatisticService;

    @GetMapping("/member-contracts")
    public ResponseEntity<List<MemberContractStatisticDto>> getMemberContractStatistics(@VendorId Long vendorId) {
        List<MemberContractStatisticDto> statistics;
        statistics = memberContractStatisticService.getMemberContractStatistics(vendorId);
        return ResponseEntity.ok(statistics);
    }
}