package kr.or.kosa.cmsplusmain.domain.payment.dto;

import kr.or.kosa.cmsplusmain.domain.payment.entity.method.CardPaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.CmsPaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.AutoPaymentType;
import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethodInfo;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.VirtualAccountPaymentType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentDto {
	private Long paymentId;
	private PaymentType paymentType;
	private PaymentStatus paymentStatus;
	private ConsentStatus consentStatus;

	// 자동결제
	private CardInfo cardInfo;
	private CMSInfo cmsInfo;

	// 가상계좌
	private VirtualAccountInfo virtualAccountInfo;

	public static PaymentDto fromEntity(Payment payment) {
		PaymentType paymentType = payment.getPaymentType();

		PaymentDto.PaymentDtoBuilder paymentDtoBuilder = PaymentDto.builder()
			.paymentId(payment.getId())
			.paymentType(paymentType)
			.paymentStatus(payment.getStatus())
			.consentStatus(payment.getConsentStatus());

		switch (paymentType) {
			case AUTO -> {
				AutoPaymentType autoPaymentType = (AutoPaymentType)payment;

				// NULLABLE
				// 신규 회원 등록시 결제수단 정보를 회원설정으로 설정시 NULL
				PaymentMethodInfo paymentMethodInfo = autoPaymentType.getPaymentMethodInfo();
				if (paymentMethodInfo == null) {
					break;
				}

				PaymentMethod paymentMethod = paymentMethodInfo.getPaymentMethod();
				switch (paymentMethod) {
					case CMS -> {
						CmsPaymentMethod cmsPaymentMethod = (CmsPaymentMethod)paymentMethodInfo;
						CMSInfo cmsInfo = CMSInfo.builder()
							.bank(cmsPaymentMethod.getBank())
							.accountNumber(cmsPaymentMethod.getAccountNumber())
							.accountOwner(cmsPaymentMethod.getAccountOwner())
							.accountOwnerBirth(cmsPaymentMethod.getAccountOwnerBirth())
							.build();
						paymentDtoBuilder = paymentDtoBuilder.cmsInfo(cmsInfo);
					}
					case CARD -> {
						CardPaymentMethod cardPaymentMethod = (CardPaymentMethod)paymentMethodInfo;
						CardInfo cardInfo = CardInfo.builder()
							.cardNumber(cardPaymentMethod.getCardNumber())
							.cardOwner(cardPaymentMethod.getCardOwner())
							.cardOwnerBirth(cardPaymentMethod.getCardOwnerBirth())
							.build();
						paymentDtoBuilder = paymentDtoBuilder.cardInfo(cardInfo);
					}
				}
			}
			case VIRTUAL -> {
				VirtualAccountPaymentType virtualAccountPaymentType = (VirtualAccountPaymentType)payment;
				VirtualAccountInfo virtualAccountInfo = VirtualAccountInfo.builder()
					.bank(virtualAccountPaymentType.getBank())
					.accountOwner(virtualAccountPaymentType.getAccountOwner())
					.accountNumber(virtualAccountPaymentType.getAccountNumber())
					.build();
				paymentDtoBuilder = paymentDtoBuilder.virtualAccountInfo(virtualAccountInfo);
			}
		}

		return paymentDtoBuilder.build();
	}
}
