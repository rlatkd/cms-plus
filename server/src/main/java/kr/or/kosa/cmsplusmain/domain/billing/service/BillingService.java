package kr.or.kosa.cmsplusmain.domain.billing.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.kosa.cmsplusmain.domain.base.dto.PageDto;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItem;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BillingService {

	private final BillingRepository billingRepository;

	public List<BillingListItem> findBillingsByContract(Long contractId, PageDto.Req pageable) {
		return billingRepository.findBillingsByContractId(contractId, pageable);
	}
}
