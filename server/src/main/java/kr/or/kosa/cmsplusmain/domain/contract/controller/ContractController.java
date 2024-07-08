package kr.or.kosa.cmsplusmain.domain.contract.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageDto;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractDetail;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractListItem;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractUpdateReq;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractSearch;
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
	public List<ContractListItem> getContractList(ContractSearch contractSearch, PageDto.Req pageRequest) {
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
		return contractService.findContractDetail(contractId);
	}

	/*
	* 계약 및 결제 수정
	*
	* 계약 상세 페이지에서 상품 수정 버튼 및 결제 수정 버튼 클릭 후
	* 나오는 수정 화면에서 호출해서 정보를 수정한다.
	* 즉, 계약과 결제 수정에 사용된다.
	* */
	@PutMapping("/{contractId}")
	public void updateContract(@PathVariable Long contractId, @RequestBody @Valid ContractUpdateReq contractUpdateReq) {
		contractService.updateContract(contractId, contractUpdateReq);
	}
}
