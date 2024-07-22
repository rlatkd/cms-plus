package kr.or.kosa.cmsplusmain.domain.kafka.service;

import jakarta.persistence.EntityNotFoundException;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingRepository;
import kr.or.kosa.cmsplusmain.domain.kafka.dto.messaging.MessageDto;
import kr.or.kosa.cmsplusmain.domain.kafka.dto.messaging.SmsMessageDto;
import kr.or.kosa.cmsplusmain.domain.kafka.dto.payment.PaymentDto;
import kr.or.kosa.cmsplusmain.domain.kafka.dto.payment.PaymentResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaPaymentService {

    @Value("${kafkaTopic.paymentTopic}")
    private String paymentTopic;

    @Value("${kafkaTopic.messagingTopic}")
    private String messagingTopic;

    private final KafkaTemplate<String, PaymentDto> paymentKafkaTemplate;
    private final KafkaTemplate<String, MessageDto> messagingKafkaTemplate;
    private final BillingRepository billingRepository;

    // 메인서버->결제서버; 결제정보 전달
    public void producePayment(PaymentDto paymentDto) {
        paymentKafkaTemplate.send(paymentTopic, paymentDto);
    }

    // 메인서버->메시징서버; 결제결과문자 전달
    public void produceMessaging(MessageDto messageDto) {
        messagingKafkaTemplate.send(messagingTopic, messageDto);
    }

    // 메인서버<-결제서버; 결제결과 받음
    @Transactional
    @KafkaListener(topics = "payment-result-topic", groupId = "payment-result-group", containerFactory = "paymentKafkaListenerContainerFactory")
    public void consumePaymentResult(List<ConsumerRecord<String, PaymentResultDto>> records) {
        for (ConsumerRecord<String, PaymentResultDto> record : records) {
            PaymentResultDto paymentResultDto = record.value();
            MessageDto messageDto = new SmsMessageDto(paymentResultDto.getResult(), paymentResultDto.getPhoneNumber());
            Billing billing = billingRepository.findById(paymentResultDto.getBillingId())
                    .orElseThrow(() -> new EntityNotFoundException(paymentResultDto.getBillingId().toString()));
            try {
                if (paymentResultDto.getResult().equals("결제성공")) {
                    billing.setPaid(); // 결제결과가 성공이면 청구상태를 결제완료로 바꿈
                    log.info("결제성공");
                } else {
                    log.error("결제실패");
                }
                produceMessaging(messageDto); // 메인서버->메시징서버; 결제결과문자 전달
            } catch (EntityNotFoundException e) {
                log.error(e.getMessage());
            }
        }
    }

}
