package kr.or.kosa.cmsplusmain.domain.messaging.service;

import kr.or.kosa.cmsplusmain.domain.messaging.SetProperties;
import kr.or.kosa.cmsplusmain.domain.messaging.dto.KafkaTestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

//	@Autowired
//	SetProperties setProperties;
//
//	@Autowired
//	private KafkaTemplate<String, String> kafkaTemplate;
//
//	public void send(KafkaTestDto kafkaTestDto) {
//		Message<String> message = MessageBuilder
//				.withPayload(kafkaTestDto.toString())
//				.setHeader(KafkaHeaders.TOPIC, setProperties.getTopic1())
//				.build();
//
//		kafkaTemplate.send(message);
//	}

	private final KafkaTemplate<String, KafkaTestDto> kafkaTemplate;

	public void send(String topic, KafkaTestDto kafkaTestDto) {
		log.error("[토픽]={} [페이로드]={}", topic, kafkaTestDto);
		kafkaTemplate.send(topic, kafkaTestDto);
	}


	public void sendSms(String phone, String message) {
		log.info("SMS 발송: {}\n{}",  phone, message);
	}

	public void sendEmail(String email, String message) {
		log.info("EMAIL 발송: {}\n{}", email, message);
	}
}
