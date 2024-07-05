package kr.or.kosa.cmsplusmain.domain.contract.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.kosa.cmsplusmain.domain.base.dto.PageDto;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractDetail;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractListItem;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractProductDto;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractSearch;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractRepository;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.messaging.MessageSendMethod;
import kr.or.kosa.cmsplusmain.domain.payment.dto.CMSInfo;
import kr.or.kosa.cmsplusmain.domain.payment.dto.CardInfo;
import kr.or.kosa.cmsplusmain.domain.payment.entity.CMSAutoPayment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.CardAutoPayment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentType;
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
		Contract contract = contractRepository.findContractById(contractId);
		return ContractDetail.from(contract);
	}
}
