package kr.or.kosa.cmsplusmain.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import kr.or.kosa.cmsplusmain.domain.kafka.dto.messaging.MessageDto;
import kr.or.kosa.cmsplusmain.domain.kafka.dto.payment.PaymentDto;

@Configuration
public class KafkaProducerConfig {

    @Value("${kafkaServer.ip}")
    private String kafkaServerIp;

    // SMS. EMAIL 메시징 서버에 보냄
    @Bean
    public KafkaTemplate<String, MessageDto> messagingKafkaTemplate() {
        return new KafkaTemplate<>(messagingProducerFactory());
    }

    @Bean
    public ProducerFactory<String, MessageDto> messagingProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerIp);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all"); // 메시지 Leader가 모든 Replica까지 Commit되면 ack를 보냄(가장 느린 속도 / 가장 높은 보장성)
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, "100000"); // 메시지 전송 실패 시 재전송 대기시간
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, "100000"); // 문자열 기준 500건씩
        props.put(ProducerConfig.LINGER_MS_CONFIG, "500"); // 만약 배치사이즈(500건)이 안 들어왔으면 마냥 대기할 수 없음; 0.5초 후에 보냄
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true"); // 멱등성 설정
        props.put(ProducerConfig.RETRIES_CONFIG, "10"); // 멱등성 설정시 값이 0보다 커야함; default 설정은 무한 재시도
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");
        return new DefaultKafkaProducerFactory<>(props);
    }

    // 결제데이터 결제서버에 보냄
    @Bean
    public KafkaTemplate<String, PaymentDto> paymentKafkaTemplate() {
        return new KafkaTemplate<>(paymentProducerFactory());
    }

    @Bean
    public ProducerFactory<String, PaymentDto> paymentProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerIp);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, "100000");
        props.put(ProducerConfig.LINGER_MS_CONFIG, "500");

        // 중복없는 전송(Exactly-once delivery) 설정 - 중복 메시지 발송 방지

        // 멱등성 설정
        // 동일 메시지를 여러 번 보내더라도 Seq로 판단하여 중복된 메시지면 무시함
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        // 전송 실패 시 재시도 횟수
        // Producer가 DELIVERY_TIMEOUT_MS 까지 재시도함
        props.put(ProducerConfig.RETRIES_CONFIG, "10");

        // 연결당 최대 동시 전송 요청 수 설정
        // 메시지의 순서를 유지하면서 최대한의 성능 보장
        // Producer와 Broker 사이에 최대 5개의 메시지 배치가 전송될 수 있음
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");

        // Leader가 모든 Replica까지 Commit되면 ACK를 보냄
        // 속도가 가장 느리지만, 메시지 송신을 가장 잘 보장해줌
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        // 메시지 전송 시도에 대한 타임아웃
        // 이 시간이 지나면 메시지 전송은 실패한 것으로 간주
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, "100000");

        return new DefaultKafkaProducerFactory<>(props);
    }

}
