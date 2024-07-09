package kr.or.kosa.cmsplusmain.domain.billing.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItem;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingSearch;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingCustomRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BillingService {

	private final BillingCustomRepository billingRepository;

	/*
	* 계약 상세 - 청구 목록 조회
	*
	* 계약 id로 청구 목록을 가져온다.
	* 최신순 정렬
	* */
	public List<BillingListItem> findBillingsByContract(Long contractId, SortPageDto.Req pageable) {
		return billingRepository.findBillingsByContractId(contractId, pageable).stream()
			.map(BillingListItem::fromEntity)
			.toList();
	}

	/*
	* 청구목록 조회
	*
	* 검색, 정렬 조건을 반영한다.
	* */
	public List<BillingListItem> findBillings(BillingSearch search, SortPageDto.Req pageable) {
		String vendorUsername = "vendor1";
		return billingRepository.findBillingListWithCondition(vendorUsername, search, pageable).stream()
			.map(BillingListItem::fromEntity)
			.toList();
	}
}
