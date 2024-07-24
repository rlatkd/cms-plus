package kr.or.kosa.cmsplusmain.config;

import kr.or.kosa.cmsplusmain.domain.kafka.dto.payment.PaymentResultDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    // 결제결과 결제서버에서 받음
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentResultDto> paymentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentResultDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentConsumerFactory());
        factory.setBatchListener(true); // 배치 설정
        factory.setConcurrency(3); // 동시성 설정
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.BATCH); // Auto Commit인 경우 배치 모드로 동작
        factory.getContainerProperties().setIdleBetweenPolls(500); // idle time 동안 메시지를 쌓고 batch 처리 가능
        return factory;
    }

    @Bean
    public ConsumerFactory<String, PaymentResultDto> paymentConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "ec2-3-39-232-243.ap-northeast-2.compute.amazonaws.com:9094");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-result-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        JsonDeserializer<PaymentResultDto> jsonDeserializer = new JsonDeserializer<>(PaymentResultDto.class, false); // false; 기본 생성자 호출 비활성화->불변 객체일 경우 성능 향상
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }

}