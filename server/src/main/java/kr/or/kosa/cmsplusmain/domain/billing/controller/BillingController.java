package kr.or.kosa.cmsplusmain.domain.billing.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.kosa.cmsplusmain.domain.base.dto.PageDto;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItem;
import kr.or.kosa.cmsplusmain.domain.billing.service.BillingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/vendor/billing")
@RequiredArgsConstructor
public class BillingController {

	private final BillingService billingService;

	@GetMapping
	public List<BillingListItem> findBillingsByContract(@RequestParam(name = "contract") Long contractId, PageDto.Req pageable) {
		return billingService.findBillingsByContract(contractId, pageable);
	}
}
