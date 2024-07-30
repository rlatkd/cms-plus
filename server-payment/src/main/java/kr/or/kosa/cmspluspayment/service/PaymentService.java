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
 import java.util.List;

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
     @KafkaListener(topics = "payment-topic", groupId = "payment-group", containerFactory = "paymentKafkaListenerContainerFactory")
     public void consumePayment(List<ConsumerRecord<String, PaymentDto>> consumerRecords) {
         for (ConsumerRecord<String, PaymentDto> consumerRecord : consumerRecords) {
             PaymentDto paymentDto = consumerRecord.value(); // 받은 결제정보 데이터
             PaymentResultDto paymentResultDto = new PaymentResultDto(); // 보낼 결제결과 데이터
             paymentResultDto.setBillingId(paymentDto.getBillingId());
             paymentResultDto.setPhoneNumber(paymentDto.getPhoneNumber());
             try {
                 if (Long.parseLong(String.valueOf(paymentDto.getNumber().charAt(paymentDto.getNumber().length() - 1))) % 2 == 0) { // Long; 계좌/카드 번호 길어지면 integer로 변환 에러남
                     paymentResultDto.setResult("안녕하세요 효성CMS#입니다. 결제가 완료되었습니다.");
                     log.error("결제성공");
                 } else {
                     paymentResultDto.setResult("안녕하세요 효성CMS#입니다. 결제에 실패했습니다.");
                     log.error("결제실패");
                 }
                 producePaymentResult(paymentResultDto); // 결제서버->메인서버; 결제결과 전달
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
     }

 }