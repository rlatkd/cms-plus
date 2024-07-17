package kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.service;

import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberDetail;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.member.repository.MemberRepository;
import kr.or.kosa.cmsplusmain.domain.member.repository.MemberCustomRepository;
import kr.or.kosa.cmsplusmain.domain.contract.service.ContractService;
import kr.or.kosa.cmsplusmain.domain.payment.service.PaymentService;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingCustomRepository;
import kr.or.kosa.cmsplusmain.domain.product.repository.ProductCustomRepository;
import kr.or.kosa.cmsplusmain.domain.product.dto.ProductListItemRes;
import kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.dto.SimpleConsentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimpleConsentService {

    private final MemberRepository memberRepository;
    private final MemberCustomRepository memberCustomRepository;
    private final BillingCustomRepository billingCustomRepository;
    private final ProductCustomRepository productCustomRepository;
    private final PaymentService paymentService;
    private final ContractService contractService;

    @Transactional
    public MemberDetail processSimpleConsent(Long vendorId, SimpleConsentDTO dto) {
        Member member = null;

        if (memberCustomRepository.idExistMemberByPhone(vendorId, dto.getPhone())) {
            member = memberCustomRepository.findMemberByPhone(vendorId, dto.getPhone()).orElseThrow();
        } else {
            member = memberRepository.save(dto.toMemberEntity(vendorId));
        }

        Payment payment = paymentService.createPayment(dto.toPaymentCreateReq());

        Map<Long, String> productIdToNameMap = productCustomRepository.findAllProductNamesById(
                dto.getItems().stream().map(ProductListItemRes::getProductId).collect(Collectors.toList())
        );

        contractService.createContract(vendorId, member, payment, dto.toContractCreateReq());

        int billingCount = billingCustomRepository.findBillingStandardByMemberId(vendorId, member.getId());
        Long totalBillingPrice = billingCustomRepository.findBillingProductByMemberId(vendorId, member.getId());

        return MemberDetail.fromEntity(member, billingCount, totalBillingPrice);
    }
}