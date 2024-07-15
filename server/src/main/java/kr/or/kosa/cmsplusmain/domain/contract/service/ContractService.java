package kr.or.kosa.cmsplusmain.domain.contract.service;

import java.util.List;

import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractProductRepository;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItemRes;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingCustomRepository;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractCreateReq;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractDetailRes;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractListItemRes;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractProductReq;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractSearchReq;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractCustomRepository;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractRepository;
import kr.or.kosa.cmsplusmain.domain.payment.dto.method.PaymentMethodInfoRes;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.PaymentTypeInfoRes;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.service.PaymentService;
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
	public PageRes<BillingListItemRes> getBillingsByContract(
		Long vendorId, Long contractId, PageReq pageReq)
	{
		// 고객의 계약 여부 확인
		validateContractUser(contractId, vendorId);

		List<BillingListItemRes> content = billingCustomRepository.findBillingsByContractId(contractId, pageReq)
			.stream()
			.map(BillingListItemRes::fromEntity)
			.toList();

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

		return ContractDetailRes.fromEntity(contract, paymentTypeInfoRes, paymentMethodInfoRes);
	}

	/*
	* 계약 수정
	*
	* 총 발생 쿼리수: 3회
	* 내용:
	* 	존재여부 확인, 계약 조회, 계약상품 목록 조회(+? batch_size=100), 신규 계약상품 생성(*N), 계약이름 수정, 계약상품 삭제(*N)
	* */
	@Transactional
	public void updateContract(Long vendorId, Long contractId, ContractCreateReq contractCreateReq) {
		// 고객의 계약 여부 확인
		validateContractUser(contractId, vendorId);

		// 계약 이름 수정
		Contract contract = contractRepository.findById(contractId).orElseThrow();
		contract.setContractName(contractCreateReq.getContractName());

		// 신규 계약상품
		List<ContractProduct> newContractProducts = contractCreateReq.getContractProducts()
			.stream()
			.map(ContractProductReq::toEntity)
			.toList();

		// 계약상품목록 수정
		updateContractProduct(contract, newContractProducts);
	}

	/*
	* 기존 계약상품과 신규 계약상품 비교해서
	* 새롭게 추가되거나 삭제된 것만 수정 반영
	* */
	private void updateContractProduct(Contract contract, List<ContractProduct> newContractProducts) {
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
	private void validateContractUser(Long contractId, Long vendorId) {
		if (!contractCustomRepository.isExistContractByUsername(contractId, vendorId)) {
			throw new EntityNotFoundException("계약 ID 없음(" + contractId + ")");
		}
	}


	public void createContract(Long vendorId, Member member, Payment payment, ContractCreateReq contractCreateReq) {

		// 계약 정보를 DB에 저장한다.
		Contract contract = contractCreateReq.toEntity(vendorId , member, payment);
		contractRepository.save(contract);

		// 계약 상품 정보를 DB에 저장한다.
		List<ContractProduct> contractProducts = contractCreateReq.toProductEntities(contract);
		contractProductRepository.saveAll(contractProducts);
	}
}
