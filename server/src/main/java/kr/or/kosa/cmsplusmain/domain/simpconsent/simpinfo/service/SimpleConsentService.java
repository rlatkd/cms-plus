

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
import kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.dto.SimpleConsentContractDTO;
import kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.dto.SimpleConsentMemberDTO;
import kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.dto.SimpleConsentPaymentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimpleConsentService {

    private final MemberRepository memberRepository;
    private final MemberCustomRepository memberCustomRepository;
    private final BillingCustomRepository billingCustomRepository;
    private final ProductCustomRepository productCustomRepository;
    private final PaymentService paymentService;
    private final ContractService contractService;

    @Transactional
    public MemberDetail processSimpleConsent(Long vendorId, SimpleConsentMemberDTO memberDTO,
                                             SimpleConsentPaymentDTO paymentDTO,
                                             SimpleConsentContractDTO contractDTO) {

        Member member = null;

        if (memberCustomRepository.idExistMemberByPhone(vendorId, memberDTO.getPhone())) {
            member = memberCustomRepository.findMemberByPhone(vendorId, memberDTO.getPhone()).orElseThrow();
        } else {
            member = memberRepository.save(memberDTO.toMemberEntity(vendorId));
        }

        Payment payment = paymentService.createPayment(paymentDTO.toPaymentCreateReq());

        Map<Long, String> productIdToNameMap = productCustomRepository.findAllProductNamesById(
                contractDTO.getItems().stream().map(ProductListItemRes::getProductId).collect(Collectors.toList())
        );

        contractService.createContract(vendorId, member, payment, contractDTO.toContractCreateReq());

        //int billingCount = billingCustomRepository.findBillingStandardByMemberId(vendorId, member.getId());
        //Long totalBillingPrice = billingCustomRepository.findBillingProductByMemberId(vendorId, member.getId());

        return MemberDetail.fromEntity(member, 0,0L);
    }
}
