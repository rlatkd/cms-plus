package kr.or.kosa.cmsplusmain.domain.billing.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.kosa.cmsplusmain.domain.base.dto.PageDto;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItem;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingSearch;
import kr.or.kosa.cmsplusmain.domain.billing.service.BillingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/vendor/billing")
@RequiredArgsConstructor
public class BillingController {

	private final BillingService billingService;

	@GetMapping("/contract")
	public List<BillingListItem> getBillingListByContract(@RequestParam(name = "id") Long contractId,
		PageDto.Req pageable) {
		return billingService.findBillingsByContract(contractId, pageable);
	}

	@GetMapping
	public List<BillingListItem> getBillingListWithCondition(BillingSearch search, PageDto.Req pageable) {
		return billingService.findBillings(search, pageable);
	}
}
