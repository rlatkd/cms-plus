package kr.or.kosa.cmsplusmain.domain.contract.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.messaging.MessageSendMethod;
import kr.or.kosa.cmsplusmain.domain.payment.dto.CMSInfo;
import kr.or.kosa.cmsplusmain.domain.payment.dto.CardInfo;
import kr.or.kosa.cmsplusmain.domain.payment.entity.CMSAutoPayment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.CardAutoPayment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class ContractDetail {

	private final Long contractId;
	private final String contractName;
	private final LocalDateTime createdDateTime;
	private final LocalDateTime modifiedDateTime;

	// 계약 상품
	private final List<ContractProductDto> contractProducts;

	// 결제 정보
	private final String paymentType;
	private final Integer contractDay;
	private final LocalDate startDate;
	private final LocalDate endDate;

	private final String paymentMethod;
	private final CardInfo cardInfo;
	private final CMSInfo cmsInfo;

	private final String consentStatus;
	private final LocalDateTime simpConsentReqDateTime;

	// 청구 정보
	private final Boolean autoBilling;
	private final Boolean autoInvoiceSend;
	private final String invoiceSendMethod;

	public static ContractDetail from(Contract contract) {
		if (contract == null) {
			return null;
		}
		List<ContractProductDto> productDtos = contract.getContractProducts().stream()
			.map(contractProduct -> ContractProductDto.builder()
				.contractProductId(contractProduct.getId())
				.name(contractProduct.getName())
				.price(contractProduct.getPrice())
				.quantity(contractProduct.getQuantity())
				.build())
			.toList();

		Payment payment = contract.getPayment();
		PaymentType paymentType = PaymentType.of(payment.getPaymentType());
		PaymentMethod paymentMethod = payment.getPaymentMethod();

		CardInfo cardInfo = null;
		CMSInfo cmsInfo = null;
		LocalDateTime simpConsentReqDateTime = null;

		if (paymentMethod == null) {}
		else if (paymentMethod.equals(PaymentMethod.CARD)) {
			CardAutoPayment cardAutoPayment = (CardAutoPayment) payment;
			cardInfo = CardInfo.builder()
				.cardNumber(cardAutoPayment.getCardNumber())
				.cardOwner(cardAutoPayment.getCardOwner())
				.cardOwnerBirth(cardAutoPayment.getCardOwnerBirth())
				.build();
			simpConsentReqDateTime = cardAutoPayment.getSimpleConsentReqDateTime();
		} else if (paymentMethod.equals(PaymentMethod.CMS)) {
			CMSAutoPayment cmsAutoPayment = (CMSAutoPayment) payment;
			cmsInfo = CMSInfo.builder()
				.bank(cmsAutoPayment.getBank())
				.accountNumber(cmsAutoPayment.getAccountNumber())
				.accountOwner(cmsAutoPayment.getAccountOwner())
				.accountOwnerBirth(cmsAutoPayment.getAccountOwnerBirth())
				.build();
			simpConsentReqDateTime = cmsAutoPayment.getSimpleConsentReqDateTime();
		}

		Member member = contract.getMember();
		MessageSendMethod sendMethod = member.getInvoiceSendMethod();

		return ContractDetail.builder()
			.contractId(contract.getId())
			.contractName(contract.getName())
			.createdDateTime(contract.getCreatedDateTime())
			.modifiedDateTime(contract.getUpdatedDateTime())
			.contractProducts(productDtos)
			.paymentType((paymentType == null) ? null : paymentType.getTitle())
			.paymentMethod((paymentMethod == null) ? null : paymentMethod.getTitle())
			.cardInfo(cardInfo)
			.cmsInfo(cmsInfo)
			.consentStatus((payment.getConsentStatus() == null) ? null : payment.getConsentStatus().getTitle())
			.simpConsentReqDateTime(simpConsentReqDateTime)
			.autoBilling(member.isAutoBilling())
			.autoInvoiceSend(member.isAutoInvoiceSend())
			.invoiceSendMethod((sendMethod == null) ? null : sendMethod.getTitle())
			.build();
	}
}
