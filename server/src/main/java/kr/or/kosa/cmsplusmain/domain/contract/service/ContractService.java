package kr.or.kosa.cmsplusmain.domain.contract.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItemDto;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingCustomRepository;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractCreateReq;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractDetailDto;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractListItemDto;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractSearchReq;
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

	private final ContractCustomRepository contractCustomRepository;
	private final BillingCustomRepository billingCustomRepository;

	/*
	 * 계약 목록 조회
	 * */
	public PageRes<ContractListItemDto> searchContracts(String vendorUsername, ContractSearchReq search, PageReq pageReq)
	{
		// 1개 페이지 결과
		List<ContractListItemDto> content = contractCustomRepository
			.findContractListWithCondition(vendorUsername, search, pageReq)
			.stream()
			.map(ContractListItemDto::fromEntity)
			.toList();

		// 전체 개수
		int totalContentCount = contractCustomRepository.countAllContracts(vendorUsername, search);

		return new PageRes<>(totalContentCount, pageReq.getSize(), content);
	}

	/*
	 * 계약의 청구 목록
	 * */
	public PageRes<BillingListItemDto> getBillingsByContract(
		String vendorUsername, Long contractId, PageReq pageReq)
	{
		// 고객의 계약 여부 확인
		validateContractUser(contractId, vendorUsername);

		List<BillingListItemDto> content = billingCustomRepository.findBillingsByContractId(contractId, pageReq)
			.stream()
			.map(BillingListItemDto::fromEntity)
			.toList();

		int totalContentCount = billingCustomRepository.countAllBillingsByContract(contractId);

		return new PageRes<>(
			totalContentCount,
			pageReq.getSize(),
			content
		);
	}

	/*
	 * 계약 상세
	 * */
	public ContractDetailDto getContractDetail(String vendorUsername, Long contractId) {
		// 고객의 계약 여부 확인
		validateContractUser(contractId, vendorUsername);

		Contract contract = contractCustomRepository.findContractDetailById(contractId).orElseThrow();
		return ContractDetailDto.fromEntity(contract);
	}

	/*
	* 계약 이름 및 상품 목록 수정
	* */
	@Transactional
	public void updateContract(
		String vendorUsername, Long contractId, ContractCreateReq contractCreateReq)
	{
		// 고객의 계약 여부 확인
		validateContractUser(contractId, vendorUsername);

		List<ContractProduct> contractProducts = contractCreateReq.getContractProducts()
			.stream()
			.map(dto -> dto.toEntity(contractId))
			.toList();

		contractCustomRepository.updateContract(
			contractId,
			contractCreateReq.getContractName(),
			contractProducts);
	}

	/*
	* 계약 ID 존재여부
	* 계약이 현재 로그인 고객의 계약인지 여부
	* */
	private void validateContractUser(Long contractId, String vendorUsername) {
		if (!contractCustomRepository.isExistContractByUsername(contractId, vendorUsername)) {
			throw new EntityNotFoundException("계약 ID 없음(" + contractId + ")");
		}
	}
}
