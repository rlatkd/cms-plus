

package kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.service;

import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractCustomRepository;
import kr.or.kosa.cmsplusmain.domain.kafka.dto.messaging.MessageDto;
import kr.or.kosa.cmsplusmain.domain.kafka.dto.messaging.SmsMessageDto;
import kr.or.kosa.cmsplusmain.domain.kafka.service.KafkaMessagingService;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberDetail;
import kr.or.kosa.cmsplusmain.domain.member.exception.MemberNotFoundException;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.member.repository.MemberRepository;
import kr.or.kosa.cmsplusmain.domain.member.repository.MemberCustomRepository;
import kr.or.kosa.cmsplusmain.domain.contract.service.ContractService;
import kr.or.kosa.cmsplusmain.domain.payment.exception.InvalidPaymentTypeException;
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
    private final ContractCustomRepository contractCustomRepository;
    private final PaymentService paymentService;
    private final ContractService contractService;
    private final KafkaMessagingService kafkaMessagingService;

    private static final String SIMPCONSENT_MESSAGE_FORMAT =
            """
            %s님의 간편동의 URL이 도착했습니다.
            
            - URL: %s
           
            """.trim();

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

//        int billingCount = billingCustomRepository.findBillingStandardByMemberId(vendorId, member.getId());
//        Long totalBillingPrice = billingCustomRepository.findBillingProductByMemberId(vendorId, member.getId());

        return MemberDetail.fromEntity(member, 0, 0L);
    }

    public void sendReqUrl(Long vendorId, Long contractId) {
        if (!contractCustomRepository.isExistContractByUsername(contractId, vendorId)) {
            throw new MemberNotFoundException("본인의 계약에만 전송 가능합니다.");
        }


        Contract contract = contractCustomRepository.findContractDetailById(contractId);
        Member member = contract.getMember();
        Payment payment = contract.getPayment();

        if (!payment.canReqSimpConsent()) {
            throw new InvalidPaymentTypeException(
                "[%s]는 간편동의가 불가능합니다".formatted(payment.getPaymentType().getTitle()));
        }

        String url = "https://localhost:8080/member/simpconsent/" + contractId;
        log.info("{} 간편동의 요청 링크가 발송됨", url);

        String text = SIMPCONSENT_MESSAGE_FORMAT.formatted(member.getName(), url).trim();
        String phone = member.getPhone();

        MessageDto messageDto = new SmsMessageDto(text, phone);

        kafkaMessagingService.produceMessaging(messageDto);

    }
}
