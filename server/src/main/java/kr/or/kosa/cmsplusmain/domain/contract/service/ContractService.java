package kr.or.kosa.cmsplusmain.domain.contract.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.kosa.cmsplusmain.domain.base.dto.PageDto;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractDetail;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractListItem;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractReq;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractSearch;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractService {

	private final ContractRepository contractRepository;

	public List<ContractListItem> findContracts(String vendorUsername, ContractSearch contractSearch, PageDto.Req pageable) {
		return contractRepository.findContractsWithCondition(vendorUsername, contractSearch, pageable);
	}

	public ContractDetail findContractDetail(Long contractId) {
		Contract contract = contractRepository.findContractDetailById(contractId);
		return ContractDetail.from(contract);
	}

	@Transactional
	public void updateContract(Long contractId, ContractReq contractReq) {
		List<ContractProduct> contractProducts = contractReq.getContractProducts().stream()
			.map(dto -> dto.toEntity(contractId))
			.toList();

		contractRepository.updateContract(
			contractId,
			contractReq.getContractName(),
			contractProducts);
	}
}
