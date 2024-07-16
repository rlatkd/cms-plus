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

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

//    @Autowired
//    private SetProperties setProperties;
//
//    @Bean
//    public KafkaAdmin kafkaAdmin() { // 카프카 클러스터 구성; SetProperties에서 가져온 값 주입
//        Map<String, Object> config = new HashMap<>();
//        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, this.setProperties.getBootstrapServers()); // 카프카 3대 서버 매핑
//        return new KafkaAdmin(config);
//    }
//
//    @Bean
//    public NewTopic newTopic() { // 카프카에 전송할 토픽 객체 생성; docker-compose에서 토픽을 선언하지 않고 이렇게 주입
//        String topicName = setProperties.getTopic1();
//        int partition = setProperties.getPartition();
//        short replication = setProperties.getReplication();
//        return new NewTopic(topicName, partition, replication);
//    }

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
