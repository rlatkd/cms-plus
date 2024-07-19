package kr.or.kosa.cmsplusmain.domain.kafka.service;

import jakarta.persistence.EntityNotFoundException;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingRepository;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaPaymentService {

    @Value("${kafkaTopic.paymentTopic}")
    private String paymentTopic;
    @Value("${kafkaTopic.paymentResultTopic}")
    private String paymentResultTopic;

    private final KafkaTemplate<String, PaymentDto> kafkaTemplate;
    private final BillingRepository billingRepository;

    // 메인서버->결제서버; 결제정보 전달
    public void producePayment(PaymentDto paymentDto) {
        kafkaTemplate.send(paymentTopic, paymentDto);
    }

    // 메인서버<-결제서버; 결제결과 받음
    @Transactional
    @KafkaListener(topics = "payment-result-topic", groupId = "payment-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumePaymentResult(ConsumerRecord<String, PaymentResultDto> record) {
        PaymentResultDto paymentResultDto = record.value();
        Billing billing = billingRepository.findById(paymentResultDto.getBillingId())
                .orElseThrow(() -> new EntityNotFoundException(paymentResultDto.getBillingId().toString()));
        try {
            // 결제결과의 result=true면 청구상태를 결제완료로 바꿈
            if (paymentResultDto.getResult()) {
                billing.setPaid();
            } else {
                log.error("결제 실패");
            }
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
        }
    }

}
