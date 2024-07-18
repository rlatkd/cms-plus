package kr.or.kosa.cmsplusmain.domain.messaging.service;

import kr.or.kosa.cmsplusmain.domain.messaging.dto.MessageDto;
import kr.or.kosa.cmsplusmain.domain.messaging.dto.TestDto;
import kr.or.kosa.cmsplusmain.domain.payment.dto.PaymentResultDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagingService {

	private final KafkaTemplate<String, MessageDto> kafkaTemplate;
	private final KafkaTemplate<String, TestDto> kafkaTemplate2;

	public void send(String topic, MessageDto messageDto) {
		log.error("[토픽]={} [페이로드]={}", topic, messageDto);
		kafkaTemplate.send(topic, messageDto);
	}



	private long startTime; // test 메서드 호출 시간 기록
	
	// 결제 시작 테스트
	public void test(String topic, TestDto testDto) {
		log.error("[토픽]={} [페이로드]={}", topic, testDto);
		kafkaTemplate2.send(topic, testDto);
		startTime = System.currentTimeMillis(); // test 메서드 호출 시간 기록

	}


	// 결제 결과 받기 테스트
	@KafkaListener(topics = "payment-result-topic", groupId = "payment-group", containerFactory = "kafkaListenerContainerFactory")
	public void consumeResult(ConsumerRecord<String, PaymentResultDto> record) {
		PaymentResultDto paymentResultDto = record.value();
		log.error("[결제 결과]: {}", paymentResultDto.toString());
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - startTime;
		log.error("[결제 결과]: {} [경과 시간]: {} ms", paymentResultDto.toString(), elapsedTime);
	}



}
