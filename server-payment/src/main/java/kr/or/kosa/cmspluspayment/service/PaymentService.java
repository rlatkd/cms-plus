package kr.or.kosa.cmspluspayment.service;

import kr.or.kosa.cmspluspayment.dto.PaymentDto;
import kr.or.kosa.cmspluspayment.dto.PaymentResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentService {

    @Value("${kafkaTopic.paymentResultTopic}")
    private String paymentResultTopic;
    private final KafkaTemplate<String, PaymentResultDto> kafkaTemplate;

    // 결제서버<-메인서버; 결제정보 받음
    @KafkaListener(topics = "payment-topic", groupId = "payment-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumeMessage(ConsumerRecord<String, PaymentDto> consumerRecord) {
        PaymentDto paymentDto = consumerRecord.value(); // 받은 결제정보 데이터
        PaymentResultDto paymentResultDto = new PaymentResultDto(); // 보낼 결제결과 데이터
        try {
            if (Long.parseLong(paymentDto.getNumber()) % 2 == 0) { // Long; 계좌/카드 번호 길어지면 integer로 변환 에러남
                paymentResultDto.setBillingId(paymentDto.getBillingId());
                paymentResultDto.setResult(true);
                log.error("유효한 결제");
                kafkaTemplate.send(paymentResultTopic, paymentResultDto); // 결제서버->메인서버; 결제결과 전달
            } else {
                // 청구 상태 로직을 보면, 우린 결제 성공만 생각하면 됨
                // paymentResultDto.setResult(false);
                log.error("유효하지 않은 결제");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



//    private final FirebaseMessaging firebaseMessaging;
//
//
//    private void sendFcmNotification(PaymentDto paymentDto) throws FirebaseMessagingException {
//        Notification notification = Notification.builder()
//                .setTitle("PAYMENT SUCCESS")
//                .setBody(paymentDto.getMessage())
//                .build();
//
//        Message message = Message.builder()
//                .setNotification(notification)
//                .setTopic("payment-topic")
//                .build();
//
//        String response = firebaseMessaging.send(message);
//        log.error("[결과물]: {}", response);
//    }

}
