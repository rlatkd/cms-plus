// package kr.or.kosa.cmspluspayment.config;

// import org.apache.kafka.common.config.TopicConfig;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.kafka.config.TopicBuilder;
// import org.springframework.kafka.core.KafkaAdmin;

// @Configuration
// public class KafkaTopicConfig {

//     @Value("${kafkaTopic.paymentResultTopic}")
//     private String paymentResultTopic;

//     @Bean
//     public KafkaAdmin.NewTopics newTopics() {
//         return new KafkaAdmin.NewTopics(
//                 TopicBuilder.name(paymentResultTopic)
//                         .partitions(3)
//                         .replicas(3)
//                         .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(1000*60*60))
//                         .build()
//         );
//     }

// }
