package kr.or.kosa.cmsplusmain.domain.billing.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItem;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingReq;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingSearch;
import kr.or.kosa.cmsplusmain.domain.billing.service.BillingService;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RestController
@RequestMapping("/api/v1/vendor/billing")
@RequiredArgsConstructor
public class BillingController {

	private final BillingService billingService;

	/*
	 * 청구목록 조회
	 * */
	@GetMapping
	public List<BillingListItem> getBillingListWithCondition(BillingSearch search, SortPageDto.Req pageable) {
		String vendorUsername = "vendor1";
		return billingService.findBillings(vendorUsername, search, pageable);
	}

	/*
	* 청구생성
	* */
	@PostMapping
	public void createBilling(@RequestBody @Valid BillingReq billingReq) {
		// TODO security
		String vendorUsername = "vendor1";
		billingService.createBilling(vendorUsername, billingReq);
	}
}
