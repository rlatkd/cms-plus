package kr.or.kosa.cmsplusmain.domain.contract.service;

import java.util.List;
import java.util.Map;

import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingSearchReq;
import kr.or.kosa.cmsplusmain.domain.contract.dto.*;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractProductRepository;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItemRes;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingCustomRepository;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractCustomRepository;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractRepository;
import kr.or.kosa.cmsplusmain.domain.payment.dto.method.PaymentMethodInfoRes;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.PaymentTypeInfoRes;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.service.PaymentService;
import kr.or.kosa.cmsplusmain.domain.product.repository.ProductCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractService {

	private final ContractRepository contractRepository;
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
	public PageRes<ContractListItemRes> searchContracts(Long vendorId, ContractSearchReq search, PageReq pageReq)
	{
		// 단일 페이지 결과
		List<ContractListItemRes> content = contractCustomRepository
			.findContractListWithCondition(vendorId, search, pageReq)
			.stream()
			.map(ContractListItemRes::fromEntity)
			.toList();

		// 전체 개수
		int totalContentCount = contractCustomRepository.countAllContracts(vendorId, search);

		return new PageRes<>(totalContentCount, pageReq.getSize(), content);
	}

	/*
	 * 계약의 청구 목록
	 *
	 * 총 발생 쿼리수: 4회
	 * 내용:
	 * 	존재여부 확인, 계약 조회, 계약상품 목록 조회(+? batch_size=100), 전체 개수 조회
	 * */
	public PageRes<BillingListItemRes> getBillingsByContract(Long vendorId, Long contractId, PageReq pageReq) {
		validateContractUser(contractId, vendorId);

		BillingSearchReq search = new BillingSearchReq();
		search.setContractId(contractId);

		List<BillingListItemRes> content =
			billingCustomRepository.findBillingListWithCondition(vendorId, search, pageReq);
		int totalContentCount = billingCustomRepository.countAllBillingsByContract(contractId);

		return new PageRes<>(
			totalContentCount,
			pageReq.getSize(),
			content
		);
	}

	/*
	 * 계약 상세
	 *
	 * 총 발생 쿼리수: 3회
	 * 내용:
	 * 	존재여부 확인, 계약 조회, 계약상품 목록 조회(+? batch_size=100)
	 * */
	public ContractDetailRes getContractDetail(Long vendorId, Long contractId) {
		// 고객의 계약 여부 확인
		validateContractUser(contractId, vendorId);

		// 계약과 멤버, 결제정보 결과 동시 조인(fetch join)
		Contract contract = contractCustomRepository.findContractDetailById(contractId);

		// 결제방식 및 수단에 따른 dto 생성
		Payment payment = contract.getPayment();
		PaymentTypeInfoRes paymentTypeInfoRes = paymentService.getPaymentTypeInfo(payment);
		PaymentMethodInfoRes paymentMethodInfoRes = paymentService.getPaymentMethodInfo(payment);

		// 계약의 청구 개수 및 총액
		Long[] totalCntAndPrice = billingCustomRepository.findBillingCntAndPriceByContract(contractId);

		return ContractDetailRes.fromEntity(contract,
			paymentTypeInfoRes, paymentMethodInfoRes,
			totalCntAndPrice[0], totalCntAndPrice[1]);
	}

	/*
	* 계약 수정
	*
	* 총 발생 쿼리수: 3회
	* 내용:
	* 	존재여부 확인, 계약 조회, 계약상품 목록 조회(+? batch_size=100), 신규 계약상품 생성(*N), 계약이름 수정, 계약상품 삭제(*N)
	* */
	@Transactional
	public void updateContract(Long vendorId, Long contractId, ContractUpdateReq contractUpdateReq) {
		// 고객의 계약 여부 확인
		validateContractUser(contractId, vendorId);

		// 계약 이름 수정
		Contract contract = contractRepository.findById(contractId).orElseThrow();
		contract.setContractName(contractUpdateReq.getContractName());

		// 신규 계약상품
		List<ContractProduct> newContractProducts = convertToContractProducts(contractUpdateReq.getContractProducts());

		// 계약상품목록 수정
		updateContractProducts(contract, newContractProducts);
	}

	/*
	 * 계약 상품 요청 -> 계약 상품 엔티티 변환 메서드
	 *
	 * 요청에서 상품의 ID를 받아서
	 * 상품의 ID를 토대로 상품 이름을 가져온다.
	 *
	 * 상품이름을 계약상품 테이블에 저장해
	 * 계약상품 조회시 상품 이름만을 가져오기위한 조인을 없앤다.
	 *
	 * 상품 설정에서 이름 수정 불가 필요
	 * */
	private List<ContractProduct> convertToContractProducts(List<ContractProductReq> contractProductReqs) {
		// 상품 ID
		List<Long> productIds = contractProductReqs.stream()
			.mapToLong(ContractProductReq::getProductId)
			.boxed().toList();

		// 상품 ID -> 이름
		Map<Long, String> productIdToName = productCustomRepository.findAllProductNamesById(productIds);

		// 청구 상품 목록
		return contractProductReqs
			.stream()
			.map(dto -> dto.toEntity(productIdToName.get(dto.getProductId())))
			.toList();
	}

	/*
	* 기존 계약상품과 신규 계약상품 비교해서
	* 새롭게 추가되거나 삭제된 것만 수정 반영
	* */
	private void updateContractProducts(Contract contract, List<ContractProduct> newContractProducts) {
		// 기존 계약상품
		List<ContractProduct> oldContractProducts = contract.getContractProducts();

		// 새롭게 추가되는 계약상품 저장
		newContractProducts.stream()
			.filter(ncp -> !oldContractProducts.contains(ncp))
			.forEach(contract::addContractProduct);

		// 없어진 계약상품 삭제
		oldContractProducts.stream()
			.filter(ocp -> !newContractProducts.contains(ocp))
			.toList()
			.forEach(contract::removeContractProduct);
	}

	/*
	* 계약 ID 존재여부
	* 계약이 현재 로그인 고객의 계약인지 여부
	* */
	public void validateContractUser(Long contractId, Long vendorId) {
		if (!contractCustomRepository.isExistContractByUsername(contractId, vendorId)) {
			throw new EntityNotFoundException("계약 ID 없음(" + contractId + ")");
		}
	}


	public Long createContract(Long vendorId, Member member, Payment payment, ContractCreateReq contractCreateReq) {
		// 계약 정보를 DB에 저장한다.
		Contract contract = contractCreateReq.toEntity(vendorId , member, payment);
		contract = contractRepository.save(contract);

		// 상품 ID -> 이름
		List<Long> productIds = contractCreateReq.getContractProducts().stream()
			.mapToLong(ContractProductReq::getProductId)
			.boxed().toList();
		Map<Long, String> productIdToName = productCustomRepository.findAllProductNamesById(productIds);

		List<ContractProduct> contractProducts = contractCreateReq.toProductEntities(contract, productIdToName);

		// 계약 상품 정보를 DB에 저장한다.
		contractProductRepository.saveAll(contractProducts);

		return contract.getId();
	}
}
