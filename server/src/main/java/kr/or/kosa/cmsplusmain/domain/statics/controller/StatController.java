package kr.or.kosa.cmsplusmain.domain.statics.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.kosa.cmsplusmain.domain.statics.dto.TopFiveMemberRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.MonthBillingInfoRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.StatInfoRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.TopInfoRes;
import kr.or.kosa.cmsplusmain.domain.statics.service.StatService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stat")
@RequiredArgsConstructor
public class StatController {

	private final StatService statService;

	@GetMapping("/info")
	public StatInfoRes getStatInfo(int year, int month) {
		Long vendorId = 1L;
		return statService.getStatInfo(vendorId, year, month);
	}

	@GetMapping("/billings")
	public MonthBillingInfoRes getBillingStat(int year, int month) {
		Long vendorId = 1L;
		return statService.getMonthBillingInfo(vendorId, year, month);
	}

	@GetMapping("/top")
	public TopInfoRes getTopFive() {
		Long vendorId = 1L;
		return statService.getTopInfo(vendorId);
	}
}
