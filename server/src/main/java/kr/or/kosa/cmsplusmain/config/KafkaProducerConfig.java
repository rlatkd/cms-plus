package kr.or.kosa.cmsplusmain.config;

import kr.or.kosa.cmsplusmain.domain.messaging.SetProperties;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Autowired
    private SetProperties setProperties;

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() { // 팩토리를 통해 만든 설정 값을 바탕으로 템플릿 생성
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() { // 설정 값을 통해 팩토리 생성
        Map<String, Object> configProps = producerFactoryConfing();
        // 카프카 토픽에 데이터를 전송하기 위해선 Producer를 생성하는 공장인
        // ProducerFactory가 필요 / default팩토리 사용
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    private Map<String, Object> producerFactoryConfing() { // 팩토리에 설정된 값을 매핑
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.setProperties.getBootstrapServers()); // 카프카 3대 서버 한 번에 세팅
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // 카프카 메시지 key String 직렬화
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // 카프카 메시지 value Json 직렬화
        return configProps;
    }
}
