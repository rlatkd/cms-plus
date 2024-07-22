package kr.or.kosa.cmsplusmain.config;


import kr.or.kosa.cmsplusmain.domain.kafka.dto.payment.PaymentResultDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class KafkaConsumerConfig {

    // 결제결과 결제서버에서 받음
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentResultDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentResultDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

//        factory.setBatchListener(true); // 카프카 배치 설정
//        factory.setConcurrency(3); // 동시성 설정 (아직까지 잘 모르겠음)
        // factory.getContainerProperties().setPollTimeout(5000); // 컨슈머가 서버로부터 데이터를 가져오는 데 걸리는 최대 시간

        return factory;
    }

    @Bean
    public ConsumerFactory<String, PaymentResultDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-result-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

//        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "20"); // 레코드 3개 쌓이면 긁어옴

        // props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, "50000"); // 최소 바이트 수
        // props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, "1000"); // 최대 대기 시간 (밀리초)

        // false; 기본 생성자 호출 비활성화->불변 객체일 경우 성능 향상
        JsonDeserializer<PaymentResultDto> jsonDeserializer = new JsonDeserializer<>(PaymentResultDto.class, false);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }

}