package kr.or.kosa.cmsplusmain.domain.billing.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItem;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingProductReq;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingReq;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingSearch;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingProduct;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStandard;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingCustomRepository;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingRepository;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingStandardRepository;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BillingService {

	private final BillingRepository billingRepository;
	private final BillingCustomRepository billingCustomRepository;
	private final BillingStandardRepository billingStandardRepository;

	private final ContractCustomRepository contractCustomRepository;


	/*
	 * 계약 상세 - 청구 목록 조회
	 *
	 * 계약 id로 청구 목록을 가져온다.
	 * */
	public List<BillingListItem> findBillingsByContract(String vendorUsername, Long contractId, SortPageDto.Req pageable) {
		return billingCustomRepository.findBillingsByContractId(contractId, pageable).stream()
			.map(BillingListItem::fromEntity)
			.toList();
	}

	/*
	 * 청구목록 조회
	 *
	 * 검색, 정렬 조건을 반영한다.
	 * */
	public List<BillingListItem> findBillings(String vendorUsername, BillingSearch search, SortPageDto.Req pageable) {
		return billingCustomRepository.findBillingListWithCondition(vendorUsername, search, pageable).stream()
			.map(BillingListItem::fromEntity)
			.toList();
	}

	/*
	* 청구 생성
	* */
	@Transactional
	public void createBilling(String vendorUsername, BillingReq billingReq) {
		// 계약 존재 여부 확인
		if (!contractCustomRepository.isExistContractByUsername(billingReq.getContractId(), vendorUsername)) {
			throw new EntityNotFoundException("해당하는 계약이 없습니다." + billingReq.getContractId().toString());
		}

		// 청구 상품
		List<BillingProduct> billingProducts = billingReq.getBillingProducts()
			.stream()
			.map(BillingProductReq::toEntity)
			.toList();

		// 청구 기준 생성
		BillingStandard billingStandard = billingStandardRepository.save(BillingStandard.builder()
			.contract(Contract.of(billingReq.getContractId()))
			.type(billingReq.getBillingType())

			// 청구 생성시 결제일을 넣어주는데 연월일 까지 넣어준다.
			// 정기 청구 시 필요한 약정일은 입력된 결제일에서 일 부분만 빼서 사용
			// ex. 입력 결제일=2024.07.13 => 약정일=13
			.contractDay(billingReq.getBillingDate().getDayOfMonth())
			.billingProducts(billingProducts)
			.build());


		// 청구 생성
		Billing billing = billingRepository.save(Billing.builder()
			.billingStandard(billingStandard)
			.billingDate(billingReq.getBillingDate())
				.billingStatus(BillingStatus.CREATED)
			.build());
	}
}
