package kr.or.kosa.cmsplusmain.domain.billing.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingCreateReq;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingDetailDto;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItemDto;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingProductReq;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingSearchReq;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingUpdateReq;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingProduct;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStandard;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusmain.domain.billing.exception.DeleteBillingException;
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
	 * 청구 생성
	 * */
	@Transactional
	public void createBilling(String vendorUsername, BillingCreateReq billingCreateReq) {
		// 계약 존재 여부 확인
		if (!contractCustomRepository.isExistContractByUsername(billingCreateReq.getContractId(), vendorUsername)) {
			throw new EntityNotFoundException();
		}

		// 청구 상품 목록
		List<BillingProduct> billingProducts = billingCreateReq.getBillingProducts()
			.stream()
			.map(BillingProductReq::toEntity)
			.toList();

		// 청구 기준 생성
		BillingStandard billingStandard = billingStandardRepository.save(BillingStandard.builder()
			.contract(Contract.of(billingCreateReq.getContractId()))
			.type(billingCreateReq.getBillingType())

			// 청구 생성시 결제일을 넣어주는데 연월일 형식으로 넣어준다.
			// 정기 청구 시 필요한 약정일은 입력된 결제일에서 일 부분만 빼서 사용
			// ex. 입력 결제일=2024.07.13 => 약정일=13
			.contractDay(billingCreateReq.getBillingDate().getDayOfMonth())
			.billingProducts(billingProducts)
			.build());


		// 청구 생성
		Billing billing = Billing.builder()
			.billingStandard(billingStandard)
			.billingDate(billingCreateReq.getBillingDate())
			.build();

		billingRepository.save(billing);
	}

	/*
	 * 청구목록 조회
	 * */
	public PageRes<BillingListItemDto> searchBillings(String vendorUsername, BillingSearchReq search, PageReq pageReq) {
		// 단일 페이지 결과
		List<BillingListItemDto> content = billingCustomRepository
			.findBillingListWithCondition(vendorUsername, search, pageReq)
			.stream()
			.map(BillingListItemDto::fromEntity)
			.toList();

		// 전체 개수
		int totalContentCount = billingCustomRepository.countAllBillings(vendorUsername, search);

		return new PageRes<>(totalContentCount, pageReq.getSize(), content);
	}

	/*
	* 청구 상세 조회
	* */
	public BillingDetailDto getBillingDetail(String vendorUsername, Long billingId) {
		// 고객의 청구 여부 확인
		validateBillingUser(billingId, vendorUsername);

		Billing billing = billingCustomRepository.findBillingDetail(billingId);
		return BillingDetailDto.fromEntity(billing);
	}

	/*
	* 청구 수정
	* */
	@Transactional
	public void updateBilling(String vendorUsername, Long billingId, BillingUpdateReq billingUpdateReq) {
		// 고객의 청구 여부 확인
		validateBillingUser(billingId, vendorUsername);

		// 결제일, 청구서 메시지 수정
		Billing billing = billingCustomRepository.findBillingWithStandard(billingId);
		billing.updateBillingDate(billingUpdateReq.getBillingDate());
		billing.setInvoiceMessage(billingUpdateReq.getInvoiceMemo());

		List<BillingProduct> billingProducts = billingUpdateReq.getBillingProducts()
			.stream()
			.map(BillingProductReq::toEntity)
			.toList();

		// 청구 상품 수정
		BillingStandard billingStandard = billing.getBillingStandard();
		billingStandard.updateBillingProducts(billingProducts);
	}

	/*
	* 청구 삭제
	* */
	@Transactional
	public void deleteBilling(String vendorUsername, Long billingId) {
		// 고객의 청구 여부 확인
		validateBillingUser(billingId, vendorUsername);

		Billing billing = billingRepository.findById(billingId).orElseThrow();

		// 청구상태에 따른 삭제 조건 존재
		// 청구상태가 생성 (청구서 발송 전)에만 삭제가 가능하다.
		if (!billing.getBillingStatus().equals(BillingStatus.CREATED)) {
			throw new DeleteBillingException();
		}
		billingRepository.deleteById(billingId);
	}

	/*
	 * 청구 ID 존재여부
	 * 청구가 현재 로그인 고객의 회원의 청구인지 여부
	 * */
	private void validateBillingUser(Long billingId, String vendorUsername) {
		if (!billingCustomRepository.isExistBillingByUsername(billingId, vendorUsername)) {
			throw new EntityNotFoundException("청구 ID 없음(" + billingId + ")");
		}
	}
}
