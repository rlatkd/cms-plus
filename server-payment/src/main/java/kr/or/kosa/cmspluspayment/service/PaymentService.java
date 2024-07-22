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
public class PaymentService {

    @Value("${kafkaTopic.paymentResultTopic}")
    private String paymentResultTopic;

    private final KafkaTemplate<String, PaymentResultDto> paymentResultKafkaTemplate;

    // 결제서버-> 메인서버; 결제결과 전달
    public void producePaymentResult(PaymentResultDto paymentResultDto) {
        paymentResultKafkaTemplate.send(paymentResultTopic, paymentResultDto);
    }

    // 결제서버<-메인서버; 결제정보 받음
    @KafkaListener(topics = "payment-topic", groupId = "payment-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumePayment(ConsumerRecord<String, PaymentDto> consumerRecord) {
        PaymentDto paymentDto = consumerRecord.value(); // 받은 결제정보 데이터
        PaymentResultDto paymentResultDto = new PaymentResultDto(); // 보낼 결제결과 데이터
        paymentResultDto.setBillingId(paymentDto.getBillingId());
        paymentResultDto.setPhoneNumber(paymentDto.getPhoneNumber());
        try {
            if (Long.parseLong(paymentDto.getNumber()) % 2 == 0) { // Long; 계좌/카드 번호 길어지면 integer로 변환 에러남
                paymentResultDto.setResult("결제성공");
                log.error("결제성공");
            } else {
                paymentResultDto.setResult("결제실패");
                log.error("결제실패");
            }
            producePaymentResult(paymentResultDto); // 결제서버->메인서버; 결제결과 전달
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
