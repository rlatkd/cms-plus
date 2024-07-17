package kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.dto;

import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.member.entity.Address;
import kr.or.kosa.cmsplusmain.domain.member.entity.MemberStatus;
import kr.or.kosa.cmsplusmain.domain.messaging.MessageSendMethod;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractCreateReq;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractProductReq;
import kr.or.kosa.cmsplusmain.domain.payment.dto.PaymentCreateReq;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.dto.method.CardMethodReq;
import kr.or.kosa.cmsplusmain.domain.payment.dto.method.CMSMethodReq;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.AutoTypeReq;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Bank;
import kr.or.kosa.cmsplusmain.domain.product.dto.ProductListItemRes;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SimpleConsentDTO {
    private String name;
    private String phone;
    private String homePhone;
    private String email;
    private String zipcode;
    private String address;
    private String addressDetail;
    private String memo;
    private LocalDate enrollDate;
    private MessageSendMethod invoiceSendMethod;
    private boolean autoInvoiceSend;
    private boolean autoBilling;

    private PaymentMethod paymentMethod;
    private String cardNumber;
    private String expiryDate;
    private String cardHolder;
    private LocalDate cardOwnerBirth;
    private String bank;
    private String accountHolder;
    private LocalDate accountOwnerBirth;
    private String accountNumber;

    private String contractName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer contractDay;
    private List<ProductListItemRes> items;
    private String signatureUrl;

    public Member toMemberEntity(Long vendorId) {
        return Member.builder()
                .vendor(Vendor.of(vendorId))
                .status(MemberStatus.ENABLED)
                .name(name)
                .email(email)
                .phone(phone)
                .homePhone(homePhone)
                .address(new Address(zipcode, address, addressDetail))
                .memo(memo)
                .invoiceSendMethod(invoiceSendMethod != null ? invoiceSendMethod : MessageSendMethod.EMAIL) // 기본값 설정
                .enrollDate(enrollDate != null ? enrollDate : LocalDate.now()) // 기본값 설정
                .autoInvoiceSend(autoInvoiceSend)
                .autoBilling(autoBilling)
                .build();
    }

    public ContractCreateReq toContractCreateReq() {
        List<ContractProductReq> contractProducts = items.stream()
                .map(item -> ContractProductReq.builder()
                        .productId(item.getProductId())
                        .price(item.getProductPrice())  // ProductListItemRes에서 가격 정보를 가져옵니다.
                        .quantity(1)  // 수량은 1로 고정되어 있습니다. 필요에 따라 변경 가능합니다.
                        .build())
                .collect(Collectors.toList());

        return ContractCreateReq.builder()
                .contractName(contractName)
                .contractStartDate(startDate)
                .contractEndDate(endDate)
                .contractDay(contractDay)
                .contractProducts(contractProducts)
                .build();
    }

    public PaymentCreateReq toPaymentCreateReq() {
        AutoTypeReq paymentTypeInfoReq = AutoTypeReq.builder()
                .consentImgUrl(signatureUrl)
                .simpleConsentReqDateTime(LocalDateTime.now())
                .build();

        if (paymentMethod == PaymentMethod.CARD) {
            CardMethodReq cardMethodReq = CardMethodReq.builder()
                    .cardNumber(cardNumber)
                    .cardMonth(Integer.parseInt(expiryDate.split("/")[0]))
                    .cardYear(Integer.parseInt(expiryDate.split("/")[1]))
                    .cardOwner(cardHolder)
                    .cardOwnerBirth(cardOwnerBirth)
                    .build();

            return PaymentCreateReq.builder()
                    .paymentTypeInfoReq(paymentTypeInfoReq)
                    .paymentMethodInfoReq(cardMethodReq)
                    .build();
        } else if (paymentMethod == PaymentMethod.CMS) {
            CMSMethodReq cmsMethodReq = CMSMethodReq.builder()
                    .bank(Bank.valueOf(bank))
                    .accountNumber(accountNumber)
                    .accountOwner(accountHolder)
                    .accountOwnerBirth(accountOwnerBirth)
                    .build();

            return PaymentCreateReq.builder()
                    .paymentTypeInfoReq(paymentTypeInfoReq)
                    .paymentMethodInfoReq(cmsMethodReq)
                    .build();
        }

        throw new IllegalStateException("Invalid payment method");
    }
}