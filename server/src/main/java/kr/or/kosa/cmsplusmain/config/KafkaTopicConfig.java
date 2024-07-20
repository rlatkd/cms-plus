package kr.or.kosa.cmsplusmain.config;

import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {

    /*
    * Kafka Broker 설정이 auto.create.topics.enable면
    * TopicConfig에서 서버 올라갈 때 topic을 생성하지 않아도
    * KafkaTemplate을 통헤 produce할 때 없는 topic이더라도 생성하면서 produce함
    * */

    @Value("${kafkaTopic.messagingTopic}")
    private String messagingTopic;

    @Value("${kafkaTopic.paymentTopic}")
    private String paymentTopic;

    @Bean
    public KafkaAdmin.NewTopics newTopics() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(messagingTopic)
                        .partitions(1)
                        .replicas(1)
                        .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(1000*60*60))
                        .build(),
                TopicBuilder.name(paymentTopic)
                        .partitions(1)
                        .replicas(1)
                        .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(1000*60*60))
                        .build(),
                TopicBuilder.name("monitoring-topic") // 추후 개발
                        .partitions(1)
                        .replicas(1)
                        .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(1000*60*60))
                        .build()
        );
    }

}
