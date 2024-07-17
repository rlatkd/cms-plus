package kr.or.kosa.cmsplusmain.config;

import kr.or.kosa.cmsplusmain.domain.messaging.SetProperties;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

//@Configuration
public class KafkaTopicConfig {

    @Bean
    public KafkaAdmin.NewTopics newTopics() {
        return new KafkaAdmin.NewTopics(
//                TopicBuilder.name("payment-topic").build(),
                TopicBuilder.name("message-topic")
                        .partitions(1)
                        .replicas(1)
                        .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(1000 * 60 * 60))
                        .build()
        );
    }
}
