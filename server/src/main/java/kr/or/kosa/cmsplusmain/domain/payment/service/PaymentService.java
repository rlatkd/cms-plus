package kr.or.kosa.cmsplusmain.domain.payment.service;

import org.springframework.stereotype.Service;

import kr.or.kosa.cmsplusmain.domain.payment.dto.method.CMSMethodRes;
import kr.or.kosa.cmsplusmain.domain.payment.dto.method.CardMethodRes;
import kr.or.kosa.cmsplusmain.domain.payment.dto.method.PaymentMethodInfoRes;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.AutoTypeRes;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.BuyerTypeRes;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.PaymentTypeInfoRes;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.VirtualAccountTypeRes;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.CardPaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.CmsPaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethodInfo;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.AutoPaymentType;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.BuyerPaymentType;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentTypeInfo;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.VirtualAccountPaymentType;

@Service
public class PaymentService {

	public PaymentTypeInfoRes getPaymentTypeInfo(Payment payment) {
		PaymentTypeInfo paymentTypeInfo = payment.getPaymentTypeInfo();
		PaymentTypeInfoRes paymentTypeInfoRes = null;

		if (paymentTypeInfo instanceof AutoPaymentType autoPaymentType) {
			paymentTypeInfoRes = AutoTypeRes.builder()
				.consentImgUrl(autoPaymentType.getConsentImgUrl())
				.signImgUrl(autoPaymentType.getSignImgUrl())
				.simpleConsentReqDateTime(autoPaymentType.getSimpleConsentReqDateTime())
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
}
