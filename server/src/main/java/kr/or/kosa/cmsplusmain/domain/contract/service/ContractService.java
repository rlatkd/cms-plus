package kr.or.kosa.cmsplusmain.domain.contract.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractDetail;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractListItem;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractSearch;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
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
	public List<ContractListItem> findContractListWithConditions(
		String vendorUsername, ContractSearch contractSearch, SortPageDto.Req pageable) {
		return contractRepository
			.findContractListWithCondition(vendorUsername, contractSearch, pageable)
			.stream()
			.map(ContractListItem::fromEntity)
			.toList();
	}

	/*
	 * 계약 상세
	 * */
	public ContractDetail findContractDetailById(Long contractId) {
		Contract contract = contractRepository.findContractDetailById(contractId).orElseThrow(
			() -> new EntityNotFoundException("계약 ID 없음(" + contractId + ")")
		);
		return ContractDetail.fromEntity(contract);
	}
	//
	// // @Transactional
	// // public Contract createContract(String vendorUsername, Long memberId, ContractCreateReq contractCreateReq) {
	// //
	// // }
	//
	// /*
	// * 계약 이름 및 상품 수정
	// * */
	// @Transactional
	// public void updateContract(Long contractId, ContractUpdateReq contractUpdateReq) {
	// 	List<ContractProduct> contractProducts = contractUpdateReq.getContractProducts().stream()
	// 		.map(dto -> dto.toEntity(contractId))
	// 		.toList();
	//
	// 	contractRepository.updateContract(
	// 		contractId,
	// 		contractUpdateReq.getContractName(),
	// 		contractProducts);
	// }
}
