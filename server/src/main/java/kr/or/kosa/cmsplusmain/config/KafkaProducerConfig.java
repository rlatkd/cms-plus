package kr.or.kosa.cmsplusmain.config;

import kr.or.kosa.cmsplusmain.domain.messaging.dto.MessageDto;
import kr.or.kosa.cmsplusmain.domain.messaging.dto.TestDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class KafkaProducerConfig {

    // SMS. EMAIL 메시징 서버에 보냄
    @Bean
    public KafkaTemplate<String, MessageDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, MessageDto> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "0"); // 가장 빠른 큐잉
        return new DefaultKafkaProducerFactory<>(props);
    }

    //--------------------------------------------------------------------------------------------------

    // 결제데이터 결제서버에 보냄
    @Bean
    public KafkaTemplate<String, TestDto> kafkaTemplate2() {
        return new KafkaTemplate<>(producerFactory2());
    }

    @Bean
    public ProducerFactory<String, TestDto> producerFactory2() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "0"); // 가장 빠른 큐잉
        return new DefaultKafkaProducerFactory<>(props);
    }




}
