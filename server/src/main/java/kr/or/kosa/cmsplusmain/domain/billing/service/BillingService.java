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

	public List<BillingListItem> findBillingsByContract(Long contractId, SortPageDto.Req pageable) {
		return billingRepository.findBillingsByContractId(contractId, pageable).stream()
			.map(BillingListItem::fromEntity)
			.toList();
	}

	public List<BillingListItem> findBillings(BillingSearch search, SortPageDto.Req pageable) {
		String vendorUsername = "vendor1";
		return billingRepository.findBillingListWithCondition(vendorUsername, search, pageable);
	}
}
