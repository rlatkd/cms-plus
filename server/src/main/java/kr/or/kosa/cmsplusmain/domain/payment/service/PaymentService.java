package kr.or.kosa.cmsplusmain.domain.payment.service;

import kr.or.kosa.cmsplusmain.domain.payment.dto.method.*;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.*;
import kr.or.kosa.cmsplusmain.domain.payment.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.CardPaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.CmsPaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethodInfo;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.AutoPaymentType;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.BuyerPaymentType;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentTypeInfo;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.VirtualAccountPaymentType;
import org.springframework.transaction.annotation.Transactional;

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

	/*
	 * 결제 방식 등록 ( 자동결제, 납부자결제, 가상계좌 )
	 * */
	@Transactional
	public void createPaymentTypeInfo(PaymentTypeInfoReq paymentTypeInfoReq) {

		// 결제방식 - 자동결제
		if(paymentTypeInfoReq instanceof AutoTypeReq autoTypeReq){
			AutoPaymentType autoPaymentType = autoTypeReq.toEntity();
			autoPaymentTypeRepository.save(autoPaymentType);
		}

		// 결제방식 - 납부자결제
		else if(paymentTypeInfoReq instanceof BuyerTypeReq buyerTypeReq){
			BuyerPaymentType buyerPaymentType = buyerTypeReq.toEntity();
			buyerPaymentTypeRepository.save(buyerPaymentType);
		}

		// 결제방식 - 가상계좌
		else if(paymentTypeInfoReq instanceof VirtualAccountTypeReq virtualAccountTypeReq){

			//TODO
			// 가상계좌 만드는 로직 필요 ( Luhn 알고리즘 )
			String accountNumber = "12345678901234";

			//TODO
			// 가상계좌 중복 체크 여부 필요

			// 가상계좌 DB에 저장
			VirtualAccountPaymentType virtualAccountPaymentType =  virtualAccountTypeReq.toEntity(accountNumber);
			virtualAccountPaymentTypeRepository.save(virtualAccountPaymentType);
		}
	}

	/*
	 * 결제 수단 정보 ( 카드, 실시간 CMS )
	 * */
	@Transactional
	public void createPaymentMethodInfo(PaymentMethodInfoReq paymentMethodInfoReq) {

		// 결제수단 - Card 결제
		if(paymentMethodInfoReq instanceof CardMethodReq cardMethodReq){
			CardPaymentMethod cardPaymentMethod = cardMethodReq.toEntity();
			cardPaymentMethodRepository.save(cardPaymentMethod);
		}

		// 결제수단 - 실시간 CMS 결제
		else if(paymentMethodInfoReq instanceof CMSMethodReq cmsMethodReq){
			CmsPaymentMethod cmsPaymentMethod = cmsMethodReq.toEntity();
			cmsPaymentMethodRepository.save(cmsPaymentMethod);
		}
	}
}
