package kr.or.kosa.cmsplusmain.domain.billing.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingCreateReq;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingDetailRes;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItemRes;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingProductReq;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingProductRes;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingSearchReq;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingUpdateReq;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingProduct;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingState;
import kr.or.kosa.cmsplusmain.domain.billing.exception.BillingNotFoundException;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingCustomRepository;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingRepository;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractCustomRepository;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.util.FormatUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BillingService {

	private final BillingRepository billingRepository;
	private final BillingCustomRepository billingCustomRepository;
	private final ContractCustomRepository contractCustomRepository;
	// private final KafkaMessagingService kafkaMessagingService;

	// 청구서 URL(청구 ID), 청구서 메시지 내용
	private static final String INVOICE_URL_FORMAT = "https://localhost:8080/invoice/%d";
	private static final String INVOICE_MESSAGE_FORMAT =
			"""
			%s님의 청구서가 도착했습니다.
			
			- 청구서명: %s
			- 납부할금액: %s원
			- 납부 기한: %s
			
			납부하기: %s
			""".trim();

	/*
	* 청구서 발송
	* */
	@Transactional
	public void sendInvoice(Long vendorId, Long billingId) {
		Billing billing = validateAndGetBilling(vendorId, billingId);
		BillingState.Field.SEND_INVOICE.validateState(billing);

		Contract contract = billing.getContract();
		Member member = contract.getMember();

		String invoiceMessage = createInvoiceMessage(billing, member);
		sendInvoiceMessage(invoiceMessage, member);

		billing.setInvoiceSent();
	}

	/*
	 * 회원이 받을 청구서 링크 문자(이메일) 내용을 만들어준다.
	 * */
	private String createInvoiceMessage(Billing billing, Member member) {
		// 메시지 내용
		String memberName = member.getName();
		String invoiceName = billing.getInvoiceName();
		String billingPrice = FormatUtil.formatMoney(billing.getBillingPrice());
		String billingDate = billing.getBillingDate().toString();
		String url = INVOICE_URL_FORMAT.formatted(billing.getId());

		String message = INVOICE_MESSAGE_FORMAT
			.formatted(memberName, invoiceName, billingPrice, billingDate, url)
			.trim();

		return message;
	}

	private void sendInvoiceMessage(String message, Member member) {
		// 청구서 링크 발송
		// MessageSendMethod sendMethod = member.getInvoiceSendMethod();
		//
		// switch (sendMethod) {
		// 	case SMS -> { SmsMessageDto smsMessageDto = new SmsMessageDto(message, member.getPhone());
		// 					kafkaMessagingService.produceMessaging(smsMessageDto); }
		// 	case EMAIL -> { EmailMessageDto emailMessageDto = new EmailMessageDto(message, member.getEmail());
		// 					kafkaMessagingService.produceMessaging(emailMessageDto); }
		// }
	}

	/*
	* 청구서 발송 취소
	* */
	@Transactional
	public void cancelInvoice(Long vendorId, Long billingId) {
		Billing billing = validateAndGetBilling(vendorId, billingId);
		BillingState.Field.CANCEL_INVOICE.validateState(billing);

		billing.setInvoiceCancelled();
	}

	/*
	* 청구 실시간 결제
	* */
	@Transactional
	public void payBilling(Long vendorId, Long billingId) {
		Billing billing = validateAndGetBilling(vendorId, billingId);
		BillingState.Field.PAY_REALTIME.validateState(billing);

		// TODO: 결제 프로세스 구현

		billing.setPaid();
	}

	/*
	* 청구 실시간 결제 취소
	* */
	@Transactional
	public void cancelPayBilling(Long vendorId, Long billingId) {
		Billing billing = validateAndGetBilling(vendorId, billingId);
		BillingState.Field.CANCEL_PAYMENT.validateState(billing);

		// TODO: 결제 취소 프로세스 구현

		billing.setPayCanceled();
	}

	/**
	 * 청구 생성 |
	 * 계약이 존재하는지 확인 후에 생성한다 |
	 * 2+n회 쿼리 발생 | 존재여부 확인, 청구 생성, 청구상품 생성 * n
	 * */
	@Transactional
	public void createBilling(Long vendorId, BillingCreateReq billingCreateReq) {
		if (!contractCustomRepository.isExistContractByUsername(billingCreateReq.getContractId(), vendorId)) {
			throw new EntityNotFoundException("해당하는 계약이 존재하지 않습니다");
		}

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
	 * 3회 쿼리 발생 | 청구목록조회, 청구상품조회, 전체 개수(페이징)
	 * */
	public PageRes<BillingListItemRes> getBillingListWithCondition(Long vendorId, BillingSearchReq search, PageReq pageReq) {
		List<BillingListItemRes> content =
			billingCustomRepository.findBillingListWithCondition(vendorId, search, pageReq);

		int totalContentCount = (int)billingCustomRepository.countBillingListWithCondition(vendorId, search);

		return new PageRes<>(totalContentCount, pageReq.getSize(), content);
	}

	/*
	* 청구 상세 조회
	* 총 발생 쿼리수: 3회
	* 내용:
	* 	존재여부 확인, 청구목록 조회, 청구상품 목록 조회(+? batch_size=100)
	* */
	public BillingDetailRes getBillingDetail(Long vendorId, Long billingId) {
		validateBillingUser(billingId, vendorId);
		Billing billing = billingCustomRepository.findBillingWithContract(billingId);

		Map<BillingState.Field, BillingState> fieldToState = Arrays.stream(BillingState.Field.values())
			.collect(Collectors.toMap(field -> field, field -> field.checkState(billing)));

		return BillingDetailRes.fromEntity(billing, fieldToState);
	}

	/**
	 * 특정 청구의 모든 청구상품 조회
	 * */
	public List<BillingProductRes> getBillingProducts(Long vendorId, Long billingId) {
		validateBillingUser(billingId, vendorId);
		return billingCustomRepository.findAllBillingProducts(billingId).stream()
			.map(BillingProductRes::fromEntity)
			.toList();
	}

	/*
	* 청구 수정
	*
	* 총 발생 쿼리수: 7회
	* 내용:
	* 	존재여부 확인, 청구 조회, 상품 이름 조회, 청구상품 목록 조회(+? batch_size=100),
	* 	청구상품 생성(*N 청구상품 수만큼), 청구 수정,
	* 	청구상품 삭제(*N 삭제된 청구상품 수만큼)
	* */
	@Transactional
	public void updateBilling(Long vendorId, Long billingId, BillingUpdateReq billingUpdateReq) {
		Billing billing = validateAndGetBilling(vendorId, billingId);
		BillingState.Field.UPDATE.validateState(billing);

		billing.updateBillingDate(billingUpdateReq.getBillingDate());
		billing.setInvoiceMessage(billingUpdateReq.getInvoiceMessage());

		List<BillingProduct> newBillingProducts = billingUpdateReq.getBillingProducts().stream()
			.map(BillingProductReq::toEntity)
			.toList();

		updateBillingProducts(billing, newBillingProducts);
	}

	/*
	* 청구 삭제
	*
	* 총 발생 쿼리수: 4회
	* 내용:
	* 	존재여부 확인, 청구 조회, 청구상품 조회(+?), 청구 삭제(*N 청구상품수)
	* */
	@Transactional
	public void deleteBilling(Long vendorId, Long billingId) {
		Billing billing = validateAndGetBilling(vendorId, billingId);
		BillingState.Field.DELETE.validateState(billing);

		billing.delete();
	}

	/*
	 * 기존 청구상품과 신규 청구상품 비교해서
	 * 새롭게 추가되거나 삭제된 것만 수정 반영
	 * */
	private void updateBillingProducts(Billing billing, List<BillingProduct> newBillingProducts) {
		// 기존 청구상품
		List<BillingProduct> oldBillingProducts = billing.getBillingProducts();

		// 새롭게 추가되는 청구상품 저장
		newBillingProducts.stream()
			.filter(nbp -> !oldBillingProducts.contains(nbp))
			.forEach(billing::addBillingProduct);

		// 없어진 청구상품 삭제
		oldBillingProducts.stream()
			.filter(obp -> !newBillingProducts.contains(obp))
			.toList()
			.forEach(billing::removeBillingProduct);
	}

	/*
	 * 청구 ID 존재여부
	 * 청구가 현재 로그인 고객의 회원의 청구인지 여부
	 * */
	private void validateBillingUser(Long billingId, Long vendorId) {
		if (!billingCustomRepository.isExistBillingByUsername(billingId, vendorId)) {
			throw new BillingNotFoundException("존재하지 않는 청구입니다");
		}
	}

	private Billing validateAndGetBilling(Long vendorId, Long billingId) {
		if (!billingCustomRepository.isExistBillingByUsername(billingId, vendorId)) {
			throw new BillingNotFoundException("존재하지 않는 청구입니다");
		}
		return billingRepository.findById(billingId)
			.orElseThrow(() -> new BillingNotFoundException("청구를 찾을 수 없습니다"));
	}
}
