package kr.or.kosa.cmsplusmessage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestComponent {
    @KafkaListener(topics = "kafka-test-topic", groupId = "kafka-test-group")
    public void listen(KafkaTestDto kafkaTestDto) {
      log.info(kafkaTestDto.toString());
    }

}
