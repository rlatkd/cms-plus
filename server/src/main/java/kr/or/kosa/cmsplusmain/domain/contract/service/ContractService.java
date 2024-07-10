package kr.or.kosa.cmsplusmain.domain.contract.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractDetail;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractListItem;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractReq;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractSearch;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractService {

	private final ContractCustomRepository contractRepository;

	/*
	 * 계약 목록 조회
	 * */
	public SortPageDto.Res<ContractListItem> findContractListWithConditions(
		String vendorUsername, ContractSearch contractSearch, SortPageDto.Req pageable) {

		List<ContractListItem> data = contractRepository
			.findContractListWithCondition(vendorUsername, contractSearch, pageable)
			.stream()
			.map(ContractListItem::fromEntity)
			.toList();
		int totalNum = contractRepository.countAllContracts(vendorUsername, contractSearch);
		int totalPage = SortPageDto.calcTotalPageNumber(totalNum, pageable.getSize());

		return new SortPageDto.Res<>(totalPage, data);
	}

	/*
	 * 계약 상세
	 * */
	public ContractDetail findContractDetailById(String vendorUsername, Long contractId) {
		validateContractUser(contractId, vendorUsername);

		Contract contract = contractRepository.findContractDetailById(contractId).orElseThrow();
		return ContractDetail.fromEntity(contract);
	}

	/*
	* 계약 이름 및 상품 목록 수정
	* */
	@Transactional
	public void updateContract(String vendorUsername, Long contractId, ContractReq contractReq) {
		validateContractUser(contractId, vendorUsername);

		List<ContractProduct> contractProducts = contractReq.getContractProducts()
			.stream()
			.map(dto -> dto.toEntity(contractId))
			.toList();

		contractRepository.updateContract(
			contractId,
			contractReq.getContractName(),
			contractProducts);
	}

	/*
	* 계약 ID 존재여부
	* 계약이 현재 로그인 고객의 계약인지 여부
	* */
	private void validateContractUser(Long contractId, String vendorUsername) {
		if (contractRepository.isExistContractByUsername(contractId, vendorUsername)) {
			throw new EntityNotFoundException("계약 ID 없음(" + contractId + ")");
		}
	}
}
