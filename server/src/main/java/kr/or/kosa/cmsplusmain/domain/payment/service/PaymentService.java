package kr.or.kosa.cmsplusmain.domain.payment.service;

import kr.or.kosa.cmsplusmain.domain.payment.dto.PaymentCreateReq;
import kr.or.kosa.cmsplusmain.domain.payment.dto.method.*;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.*;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.*;
import kr.or.kosa.cmsplusmain.domain.payment.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.CardPaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.CmsPaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethodInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService {

	private final VirtualAccountPaymentTypeRepository virtualAccountPaymentTypeRepository;
	private final BuyerPaymentTypeRepository buyerPaymentTypeRepository;
	private final AutoPaymentTypeRepository autoPaymentTypeRepository;
	private final CardPaymentMethodRepository cardPaymentMethodRepository;
	private final CmsPaymentMethodRepository cmsPaymentMethodRepository;
	private final PaymentRepository paymentRepository;

	public PaymentTypeInfoRes getPaymentTypeInfo(Payment payment) {
		PaymentTypeInfo paymentTypeInfo = payment.getPaymentTypeInfo();
		PaymentTypeInfoRes paymentTypeInfoRes = null;

		if (paymentTypeInfo instanceof AutoPaymentType autoPaymentType) {
			paymentTypeInfoRes = AutoTypeRes.builder()
				.consentImgUrl(autoPaymentType.getConsentImgUrl())
				.signImgUrl(autoPaymentType.getSignImgUrl())
				.simpleConsentReqDateTime(autoPaymentType.getSimpleConsentReqDateTime())
				.consentStatus(autoPaymentType.getConsentStatus())
				.build();
		}
		else if (paymentTypeInfo instanceof BuyerPaymentType buyerPaymentType) {
			paymentTypeInfoRes = BuyerTypeRes.builder()
				.availableMethods(buyerPaymentType.getAvailableMethods())
				.build();
		}
		else if (paymentTypeInfo instanceof VirtualAccountPaymentType virtualAccountPaymentType) {
			paymentTypeInfoRes = VirtualAccountTypeRes.builder()
				.bank(virtualAccountPaymentType.getBank())
				.accountNumber(virtualAccountPaymentType.getAccountNumber())
				.accountOwner(virtualAccountPaymentType.getAccountOwner())
				.build();
		}

		return paymentTypeInfoRes;
	}

	public PaymentMethodInfoRes getPaymentMethodInfo(Payment payment) {
		PaymentMethodInfo paymentMethodInfo = payment.getPaymentMethodInfo();
		PaymentMethodInfoRes paymentMethodInfoRes = null;

		if (paymentMethodInfo instanceof CardPaymentMethod cardPaymentMethod) {
			paymentMethodInfoRes = CardMethodRes.builder()
				.cardNumber(cardPaymentMethod.getCardNumber())
				.cardOwner(cardPaymentMethod.getCardOwner())
				.cardOwnerBirth(cardPaymentMethod.getCardOwnerBirth())
				.build();
		}
		else if (paymentMethodInfo instanceof CmsPaymentMethod cmsPaymentMethod) {
			paymentMethodInfoRes = CMSMethodRes.builder()
				.bank(cmsPaymentMethod.getBank())
				.accountOwner(cmsPaymentMethod.getAccountOwner())
				.accountNumber(cmsPaymentMethod.getAccountNumber())
				.accountOwnerBirth(cmsPaymentMethod.getAccountOwnerBirth())
				.build();
		}

		return paymentMethodInfoRes;
	}

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

	private static String generateRandomAccountNumber(int length) {
		StringBuilder accountNumber = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			accountNumber.append(ThreadLocalRandom.current().nextInt(10));
		}
		return accountNumber.toString();
	}
}
