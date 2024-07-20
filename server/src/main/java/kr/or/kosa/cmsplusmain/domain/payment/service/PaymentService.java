package kr.or.kosa.cmsplusmain.domain.payment.service;

import jakarta.persistence.EntityNotFoundException;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractRepository;
import kr.or.kosa.cmsplusmain.domain.contract.service.ContractService;
import kr.or.kosa.cmsplusmain.domain.payment.dto.PaymentCreateReq;
import kr.or.kosa.cmsplusmain.domain.payment.dto.PaymentUpdateReq;
import kr.or.kosa.cmsplusmain.domain.payment.dto.method.*;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.*;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.*;
import kr.or.kosa.cmsplusmain.domain.payment.repository.*;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;

import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.CardPaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.CmsPaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethodInfo;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import com.querydsl.jpa.hibernate.HibernateUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

	private final VirtualAccountPaymentTypeRepository virtualAccountPaymentTypeRepository;
	private final BuyerPaymentTypeRepository buyerPaymentTypeRepository;
	private final AutoPaymentTypeRepository autoPaymentTypeRepository;
	private final CardPaymentMethodRepository cardPaymentMethodRepository;
	private final CmsPaymentMethodRepository cmsPaymentMethodRepository;
	private final PaymentRepository paymentRepository;
	private final ContractRepository contractRepository;
//	private final ContractService contractService;
	private final PaymentCustomRepository paymentCustomRepository;

	public PaymentTypeInfoRes getPaymentTypeInfo(Payment payment) {
		PaymentTypeInfo paymentTypeInfo = payment.getPaymentTypeInfo();

		// proxy 객체인 경우 자식 클래스로 캐스팅이 안된다.
		// 프록시를 자식 객체로써 받아온다.
		if (!Hibernate.isInitialized(paymentTypeInfo)) {
			LazyInitializer lazyInitializer = HibernateProxy.extractLazyInitializer(paymentTypeInfo);
			if (lazyInitializer != null) {
				paymentTypeInfo = (PaymentTypeInfo) lazyInitializer.getImplementation();
			} else {
				throw new IllegalStateException("Provided payment type is not initialized");
			}
		}

		PaymentType paymentType = paymentTypeInfo.getPaymentType();

		PaymentTypeInfoRes paymentTypeInfoRes = switch (paymentType) {
			case VIRTUAL -> {
				VirtualAccountPaymentType virtualAccountPaymentType = (VirtualAccountPaymentType) paymentTypeInfo;
				yield VirtualAccountTypeRes.builder()
					.bank(virtualAccountPaymentType.getBank())
					.accountNumber(virtualAccountPaymentType.getAccountNumber())
					.accountOwner(virtualAccountPaymentType.getAccountOwner())
					.build();
			}
			case BUYER -> {
				BuyerPaymentType buyerPaymentType = (BuyerPaymentType) paymentTypeInfo;
				yield BuyerTypeRes.builder()
					.availableMethods(buyerPaymentType.getAvailableMethods())
					.build();
			}
			case AUTO -> {
				AutoPaymentType autoPaymentType = (AutoPaymentType) paymentTypeInfo;
				yield AutoTypeRes.builder()
					.consentImgUrl(autoPaymentType.getConsentImgUrl())
					.signImgUrl(autoPaymentType.getSignImgUrl())
					.simpleConsentReqDateTime(autoPaymentType.getSimpleConsentReqDateTime())
					.consentStatus(autoPaymentType.getConsentStatus())
					.build();
			}
		};

		return paymentTypeInfoRes;
	}

	public PaymentMethodInfoRes getPaymentMethodInfo(Payment payment) {
		PaymentMethodInfo paymentMethodInfo = payment.getPaymentMethodInfo();

		// 결제수단은 자동결제방식의 경우에만 존재한다.
		if (paymentMethodInfo == null) {
			return null;
		}

		// proxy 객체인 경우 자식 클래스로 캐스팅이 안된다.
		// 프록시를 자식 객체로써 받아온다.
		if (!Hibernate.isInitialized(paymentMethodInfo)) {
			LazyInitializer lazyInitializer = HibernateProxy.extractLazyInitializer(paymentMethodInfo);
			if (lazyInitializer != null) {
				paymentMethodInfo = (PaymentMethodInfo) lazyInitializer.getImplementation();
			} else {
				throw new IllegalStateException("Provided payment method is not initialized");
			}
		}

		PaymentMethod paymentMethod = paymentMethodInfo.getPaymentMethod();
		PaymentMethodInfoRes paymentMethodInfoRes = switch (paymentMethod) {
			case CARD -> {
				CardPaymentMethod cardPaymentMethod = (CardPaymentMethod) paymentMethodInfo;
				yield CardMethodRes.builder()
					.cardNumber(cardPaymentMethod.getCardNumber())
					.cardOwner(cardPaymentMethod.getCardOwner())
					.cardOwnerBirth(cardPaymentMethod.getCardOwnerBirth())
					.cardMonth(cardPaymentMethod.getCardMonth())
					.cardYear(cardPaymentMethod.getCardYear())
					.build();
			}
			case CMS -> {
				CmsPaymentMethod cmsPaymentMethod = (CmsPaymentMethod) paymentMethodInfo;
				yield CMSMethodRes.builder()
					.bank(cmsPaymentMethod.getBank())
					.accountOwner(cmsPaymentMethod.getAccountOwner())
					.accountNumber(cmsPaymentMethod.getAccountNumber())
					.accountOwnerBirth(cmsPaymentMethod.getAccountOwnerBirth())
					.build();

			}
			case ACCOUNT -> null;
		};

		return paymentMethodInfoRes;
	}

	/*
	 * 회원 등록 - 결제 정보
	 * */
	@Transactional
	public Payment createPayment(PaymentCreateReq paymentCreateReq) {

		// 결제 방식 정보 ( 자동결제, 납부자결제, 가상계좌 )
		PaymentTypeInfoReq paymentTypeInfoReq = paymentCreateReq.getPaymentTypeInfoReq();
		PaymentTypeInfo paymentTypeInfo = createPaymentTypeInfo(paymentTypeInfoReq);

		// 결제 방식 정보가 자동결제인 경우 : 결제 수단을 등록한다.
		PaymentMethodInfo paymentMethodInfo = null;
		PaymentMethodInfoReq paymentMethodInfoReq = paymentCreateReq.getPaymentMethodInfoReq();
		if(paymentMethodInfoReq != null) {

			// 결제 수단 정보 ( 카드, 실시간CMS )
			paymentMethodInfo = createPaymentMethodInfo(paymentMethodInfoReq);
		}

		// 결제 정보
		Payment payment = Payment.builder()
				.paymentType(paymentTypeInfoReq.getPaymentType())   										  // 결제방식
				.paymentTypeInfo(paymentTypeInfo)  															  // 결제방식 정보
				.paymentMethod(paymentMethodInfoReq != null ? paymentMethodInfoReq.getPaymentMethod() : null) // 결제수단
				.paymentMethodInfo(paymentMethodInfo)														  // 결제수단 정보
				.build();

		return paymentRepository.save(payment);
	}

	/*
	 * 회원 수정 - 결제 정보
	 * */
	@Transactional
	public void updatePayment(Long vendorId, Long contractId,  PaymentUpdateReq paymentUpdateReq){

		//TODO
		//contractService를 호출 하는 순간 "순환 의존성" 문제가 생긴다 왜 샌기는걸지 알아보자.

		// 고객의 계약 여부 확인
//		contractService.validateContractUser(contractId, vendorId);
		Contract contract = contractRepository.findById(contractId).orElseThrow(IllegalArgumentException::new);

		// 고객의 결제 여부 확인
		Long paymentId = contract.getPayment().getId();
		validatePaymentUser(paymentId);
		Payment payment = paymentRepository.findById(paymentId).orElseThrow(IllegalArgumentException::new);

		/*--  원래 결제 테이블의 결제 타입  --*/
		// 격제 방식 분류
		PaymentType paymentType = payment.getPaymentType();

	}

	/*
	 * 결제 방식 등록 ( 자동결제, 납부자결제, 가상계좌 )
	 * */
	private PaymentTypeInfo createPaymentTypeInfo(PaymentTypeInfoReq paymentTypeInfoReq) {
		PaymentTypeInfo paymentTypeInfo = null;

		// 결제방식 - 자동결제
		if(paymentTypeInfoReq instanceof AutoTypeReq autoTypeReq){
			AutoPaymentType autoPaymentType = autoTypeReq.toEntity();
			paymentTypeInfo = autoPaymentTypeRepository.save(autoPaymentType);
		}

		// 결제방식 - 납부자결제
		else if(paymentTypeInfoReq instanceof BuyerTypeReq buyerTypeReq){
			BuyerPaymentType buyerPaymentType = buyerTypeReq.toEntity();
			paymentTypeInfo = buyerPaymentTypeRepository.save(buyerPaymentType);
		}

		// 결제방식 - 가상계좌
		else if(paymentTypeInfoReq instanceof VirtualAccountTypeReq virtualAccountTypeReq){

			// 가상계좌 14자리 랜덤으로 생성
			String accountNumber = generateRandomAccountNumber(14);

			// 가상계좌 DB에 저장
			VirtualAccountPaymentType virtualAccountPaymentType =  virtualAccountTypeReq.toEntity(accountNumber);
			paymentTypeInfo = virtualAccountPaymentTypeRepository.save(virtualAccountPaymentType);
		}

		return paymentTypeInfo;
	}

	/*
	 * 결제 수단 정보 ( 카드, 실시간 CMS )
	 * */
	private PaymentMethodInfo createPaymentMethodInfo(PaymentMethodInfoReq paymentMethodInfoReq) {
		PaymentMethodInfo paymentMethodInfo = null;

		// 결제수단 - Card 결제
		if(paymentMethodInfoReq instanceof CardMethodReq cardMethodReq){
			CardPaymentMethod cardPaymentMethod = cardMethodReq.toEntity();
			paymentMethodInfo = cardPaymentMethodRepository.save(cardPaymentMethod);
		}

		// 결제수단 - 실시간 CMS 결제
		else if(paymentMethodInfoReq instanceof CMSMethodReq cmsMethodReq){
			CmsPaymentMethod cmsPaymentMethod = cmsMethodReq.toEntity();
			paymentMethodInfo = cmsPaymentMethodRepository.save(cmsPaymentMethod);
		}

		return paymentMethodInfo;
	}

	/*
	 * 가상 계좌 랜덤 생성
	 */
	private static String generateRandomAccountNumber(int length) {
		StringBuilder accountNumber = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			accountNumber.append(ThreadLocalRandom.current().nextInt(10));
		}
		return accountNumber.toString();
	}

	/*
	 * 결제 ID 존재여부
	 * */
	private void validatePaymentUser(Long paymentId) {
		if(!paymentCustomRepository.isExistPaymentId(paymentId)) {
			throw new EntityNotFoundException("결제 ID 없음("+paymentId + ")");
		}
	}

}
