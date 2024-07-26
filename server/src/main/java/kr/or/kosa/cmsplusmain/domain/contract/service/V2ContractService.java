package kr.or.kosa.cmsplusmain.domain.contract.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItemRes;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingSearchReq;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingCustomRepository;
import kr.or.kosa.cmsplusmain.domain.billing.repository.V2BillingRepository;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractSearchReq;
import kr.or.kosa.cmsplusmain.domain.contract.dto.V2ContractListItemRes;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractCustomRepository;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractProductRepository;
import kr.or.kosa.cmsplusmain.domain.contract.repository.V2ContractRepository;
import kr.or.kosa.cmsplusmain.domain.payment.service.PaymentService;
import kr.or.kosa.cmsplusmain.domain.product.repository.ProductCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class V2ContractService {

	private final V2ContractRepository contractRepository;
	private final V2BillingRepository billingRepository;

	private final ContractCustomRepository contractCustomRepository;
	private final BillingCustomRepository billingCustomRepository;
	private final ProductCustomRepository productCustomRepository;

	private final PaymentService paymentService;
	private final ContractProductRepository contractProductRepository;

	/*
	 * 계약 목록 조회
	 *
	 * 총 발생 쿼리수: 3회
	 * 내용:
	 * 	계약 조회, 계약상품 목록 조회(+? batch_size=100), 전체 개수 조회
	 * */
	public PageRes<V2ContractListItemRes> searchContracts(Long vendorId, ContractSearchReq search, PageReq pageReq) {
		// 단일 페이지 결과
		List<V2ContractListItemRes> content = contractRepository.searchContracts(vendorId, search, pageReq);

		// 전체 개수
		long totalContentCount = contractRepository.countSearchedContracts(vendorId, search);

		return new PageRes<>((int) totalContentCount, pageReq.getSize(), content);
	}

	public PageRes<BillingListItemRes> getBillingsByContract(Long vendorId, Long contractId, PageReq pageReq) {
		BillingSearchReq searchReq = new BillingSearchReq();
		searchReq.setContractId(contractId);

		List<BillingListItemRes> content = billingRepository.searchBillings(vendorId, searchReq, pageReq);
		long totalContentCount = billingRepository.countSearchedBillings(vendorId, searchReq);

		return new PageRes<>((int) totalContentCount, pageReq.getSize(), content);
	}
}
