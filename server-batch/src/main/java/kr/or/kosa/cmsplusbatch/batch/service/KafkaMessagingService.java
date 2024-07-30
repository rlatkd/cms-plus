package kr.or.kosa.cmsplusbatch.batch.service;

import kr.or.kosa.cmsplusbatch.batch.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessagingService {

    @Value("${kafkaTopic.messagingTopic}")
    private String messagingTopic;

    private final KafkaTemplate<String, MessageDto> messagingkafkaTemplate;

    public void produceMessaging(MessageDto messageDto) {
        messagingkafkaTemplate.send(messagingTopic, messageDto);
    }

}
