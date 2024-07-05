package kr.or.kosa.cmsplusmain.domain.contract.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.kosa.cmsplusmain.domain.base.dto.PageDto;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractDetail;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractListItem;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractSearch;
import kr.or.kosa.cmsplusmain.domain.contract.service.ContractService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/vendor/contract")
@RequiredArgsConstructor
public class ContractController {

	private final ContractService contractService;

	@GetMapping
	public List<ContractListItem> findAllContracts(ContractSearch contractSearch, PageDto.Req pageable) {
		// TODO security
		return contractService.findContracts("vendor1", contractSearch, pageable);
	}

	@GetMapping("/{contractId}")
	public ContractDetail getContract(@PathVariable Long contractId) {
		return contractService.findContractDetail(contractId);
	}
}
