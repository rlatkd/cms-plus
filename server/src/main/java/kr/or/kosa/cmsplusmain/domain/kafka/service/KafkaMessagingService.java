package kr.or.kosa.cmsplusmain.domain.kafka.service;

import kr.or.kosa.cmsplusmain.domain.kafka.dto.messaging.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaMessagingService {

	@Value("${kafkaTopic.messagingTopic}")
	private String messagingTopic;
	private final KafkaTemplate<String, MessageDto> kafkaTemplate;

	public void produceMessaging(MessageDto messageDto) {
		kafkaTemplate.send(messagingTopic, messageDto);
	}

}