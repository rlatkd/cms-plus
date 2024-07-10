package kr.or.kosa.cmsplusmain.domain.contract.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItem;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractDetail;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractListItem;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractReq;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractSearch;
import kr.or.kosa.cmsplusmain.domain.contract.service.ContractService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/vendor/contract")
@RequiredArgsConstructor
public class ContractController {

	private final ContractService contractService;

	/*
	 * 계약 목록 조회
	 *
	 * 검색, 정렬, 페이징
	 * */
	@GetMapping
	public SortPageDto.Res<ContractListItem> getContractList(ContractSearch contractSearch, SortPageDto.Req pageRequest) {
		// TODO security
		String vendorUsername = "vendor1";
		return contractService.findContractListWithConditions(vendorUsername, contractSearch, pageRequest);
	}

	/*
	 * 계약 상세 조회
	 *
	 * 계약 상세 페이지에서 청구목록을 뺀 나머지 정보 제공
	 * (청구정보에서 청구서 발송 수단, 자동 청구서 발송, 자동 청구 생성은 포함되어서 보내진다.)
	 * */
	@GetMapping("/{contractId}")
	public ContractDetail getContractDetail(@PathVariable Long contractId) {
		String vendorUsername = "vendor1";
		return contractService.findContractDetailById(vendorUsername, contractId);
	}

	/*
	 * 계약 상세 조회 - 청구 목록
	 * */
	@GetMapping("/{contractId}/billing")
	public SortPageDto.Res<BillingListItem> getBillingListByContract(@PathVariable Long contractId, SortPageDto.Req pageable) {
		String vendorUsername = "vendor1";
		return contractService.findBillingsByContract(vendorUsername, contractId, pageable);
	}

	/*
	 * 계약 및 결제 수정
	 *
	 * 계약 상세 페이지에서 상품 수정 버튼 및 결제 수정 버튼 클릭 후
	 * 나오는 수정 화면에서 호출해서 정보를 수정한다.
	 * 즉, 계약과 결제 수정에 사용된다.
	 * */
	@PutMapping("/{contractId}")
	public void updateContract(@PathVariable Long contractId, @RequestBody ContractReq contractReq) {
		String vendorUsername = "vendor1";
		contractService.updateContract(vendorUsername, contractId, contractReq);
	}
}
