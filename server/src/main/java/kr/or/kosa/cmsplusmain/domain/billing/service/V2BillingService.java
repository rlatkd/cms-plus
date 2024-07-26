package kr.or.kosa.cmsplusmain.domain.billing.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingCreateReq;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingDetailRes;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItemRes;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingProductReq;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingSearchReq;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingUpdateReq;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingProduct;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingState;
import kr.or.kosa.cmsplusmain.domain.billing.exception.BillingNotFoundException;
import kr.or.kosa.cmsplusmain.domain.billing.repository.V2BillingProductRepository;
import kr.or.kosa.cmsplusmain.domain.billing.repository.V2BillingRepository;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class V2BillingService {
	private final V2BillingRepository billingRepository;
	private final V2BillingProductRepository billingProductRepository;

	@Transactional
	public void createBilling(Long vendorId, BillingCreateReq billingCreateReq) {
		// TODO 고객의 계약 여부 확인

		List<BillingProduct> billingProducts = billingCreateReq.getProducts().stream()
			.map(BillingProductReq::toEntity)
			.toList();

		Billing billing = new Billing(
			Contract.of(billingCreateReq.getContractId()),
			billingCreateReq.getBillingType(),
			billingCreateReq.getBillingDate(),
			// 청구 생성시 결제일을 넣어주는데 연월일 형식으로 넣어준다.
			// 정기 청구 시 필요한 약정일은 입력된 결제일에서 일 부분만 빼서 사용
			// ex. 입력 결제일=2024.07.13 => 약정일=13
			billingCreateReq.getBillingDate().getDayOfMonth(),
			billingProducts);
		billingRepository.save(billing);
	}

	/**
	 * 청구목록 조회 |
	 * 3+x회 쿼리 발생 | 청구목록조회, 청구상품조회 * x(배치사이즈: 100), 전체 개수(페이징)
	 * */
	public PageRes<BillingListItemRes> searchBillings(Long vendorId, BillingSearchReq search, PageReq pageReq) {
		List<BillingListItemRes> content =
			billingRepository.searchBillings(vendorId, search, pageReq);

		int totalContentCount = (int)billingRepository.countSearchedBillings(vendorId, search);

		return new PageRes<>(totalContentCount, pageReq.getSize(), content);
	}

	/**
	 * 청구상세 조회 |
	 * 2회 쿼리 발생 | 청구존재여부, 청구상세
	 * */
	public BillingDetailRes getBillingDetail(Long vendorId, Long billingId) {
		validateBillingOwner(billingId, vendorId);
		Billing billing = billingRepository.findBillingWithContract(billingId);

		Map<BillingState.Field, BillingState> fieldToState = Arrays.stream(BillingState.Field.values())
			.collect(Collectors.toMap(field -> field, field -> field.checkState(billing)));

		return BillingDetailRes.fromEntity(billing, fieldToState);
	}

	/**
	 * 청구 수정
	 * 6+a+b+x회 쿼리 발생 | 청구존재여부, 청구 조회, 청구상품 조회 * x(배치사이즈: 100), 청구상품 생성 * a, 청구 수정, 청구상품 수정 * b, 청구상품 삭제
	 * */
	@Transactional
	public void updateBilling(Long vendorId, Long billingId, BillingUpdateReq billingUpdateReq) {
		validateBillingOwner(billingId, vendorId);
		Billing billing = billingRepository.findById(billingId);
		BillingState.Field.UPDATE.validateState(billing);

		billing.updateBillingDate(billingUpdateReq.getBillingDate());
		billing.setInvoiceMessage(billingUpdateReq.getInvoiceMessage());

		List<BillingProduct> newBillingProducts = billingUpdateReq.getBillingProducts().stream()
			.map(BillingProductReq::toEntity)
			.toList();

		updateBillingProducts(billing, newBillingProducts);
	}

	/**
	 * 기존 청구상품과 신규 청구상품 비교해서
	 * 새롭게 추가되거나 삭제된 것 그리고 수정된것 반영
	 * */
	private void updateBillingProducts(Billing billing, List<BillingProduct> mNewBillingProducts) {

		List<BillingProduct> oldBillingProducts = billing.getBillingProducts();
		List<BillingProduct> newBillingProducts = new ArrayList<>(mNewBillingProducts);

		// 삭제될 청구상품 ID
		// 삭제 상태 수정을 IN을 사용해 업데이트 쿼리 횟수 감소 목적
		List<Long> toRemoveIds = new ArrayList<>();

		// 수정될 청구상품 수정
		for (BillingProduct oldBillingProduct : oldBillingProducts) {

			// 수정된 청구상품은 리스트에서 삭제해서 insert 되지 않도록 한다.
			BillingProduct updatedNewBillingProduct = null;

			for (BillingProduct newBillingProduct : newBillingProducts) {
				if (newBillingProduct.equals(oldBillingProduct)) {
					oldBillingProduct.update(newBillingProduct.getPrice(), newBillingProduct.getQuantity());
					updatedNewBillingProduct = newBillingProduct;
					break;
				}
			}

			// 신규 상품 목록에 없는 기존 상품은 삭제한다.
			if (updatedNewBillingProduct == null) {
				toRemoveIds.add(oldBillingProduct.getId());
			} else {
				newBillingProducts.remove(updatedNewBillingProduct);
			}
		}

		// 새롭게 추가되는 청구상품 저장
		newBillingProducts.forEach(billing::addBillingProduct);

		// 없어진 청구상품 삭제
		billingProductRepository.deleteAllById(toRemoveIds);
	}

	/**
	 * 청구 존재여부
	 * 청구가 현재 로그인 고객의 청구이면서 존재하는 청구 여부 확인
	 * */
	private void validateBillingOwner(Long billingId, Long vendorId) {
		if (!billingRepository.existByVendorId(billingId, vendorId)) {
			throw new BillingNotFoundException("존재하지 않는 청구입니다");
		}
	}
}
