package kr.or.kosa.cmsplusmain.domain.contract.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageDto;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractDetail;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractListItem;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractUpdateReq;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractSearch;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractRepository;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.payment.dto.CMSInfo;
import kr.or.kosa.cmsplusmain.domain.payment.dto.CardInfo;
import kr.or.kosa.cmsplusmain.domain.payment.dto.PaymentDto;
import kr.or.kosa.cmsplusmain.domain.payment.dto.VirtualAccountInfo;
import kr.or.kosa.cmsplusmain.domain.payment.entity.AutoPayment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.CardPayment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.CmsPayment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentMethodInfo;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentType;
import kr.or.kosa.cmsplusmain.domain.payment.entity.VirtualAccountPayment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractService {

	private final ContractRepository contractRepository;

	/*
	* 계약 목록 조회
	* */
	public List<ContractListItem> findContractListWithConditions(String vendorUsername, ContractSearch contractSearch, PageDto.Req pageable) {
		return contractRepository.findContractListWithCondition(vendorUsername, contractSearch, pageable);
	}

	/*
	* 계약 상세
	* */
	public ContractDetail findContractDetail(Long contractId) {
		Contract contract = contractRepository.findContractDetailById(contractId);
		return ContractDetail.fromEntity(contract);
	}

	// @Transactional
	// public Contract createContract(String vendorUsername, Long memberId, ContractCreateReq contractCreateReq) {
	//
	// }

	@Transactional
	public void updateContract(Long contractId, ContractUpdateReq contractUpdateReq) {
		List<ContractProduct> contractProducts = contractUpdateReq.getContractProducts().stream()
			.map(dto -> dto.toEntity(contractId))
			.toList();

		contractRepository.updateContract(
			contractId,
			contractUpdateReq.getContractName(),
			contractProducts);
	}
}
