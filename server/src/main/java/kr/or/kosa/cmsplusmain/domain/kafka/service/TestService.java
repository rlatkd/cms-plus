package kr.or.kosa.cmsplusmain.domain.kafka.service;

import kr.or.kosa.cmsplusmain.domain.kafka.dto.TestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    @Value("${kafkaTopic.monitoringTopic}")
    private String monitoringTopic;

    private final KafkaTemplate<String, TestDto> monitoringKafkaTemplate;

    private AtomicInteger singleTestCounter = new AtomicInteger(0);
    private long singleTestStartTime;
    private long batchTestStartTime;

    public void produceSingleTest(int messageCount) {

        singleTestCounter.set(messageCount);
        singleTestStartTime = System.currentTimeMillis();

        for (int i = 1; i <= messageCount; i++) {
            TestDto testDto = new TestDto();
            testDto.setCount(String.valueOf(i));
            monitoringKafkaTemplate.send("monitoring-single-topic", testDto);
        }
    }

    public void produceBatchTest(int messageCount) {

        batchTestStartTime = System.currentTimeMillis();

        for (int i = 1; i <= messageCount; i++) {
            TestDto testDto = new TestDto();
            testDto.setCount(String.valueOf(i));
            monitoringKafkaTemplate.send("monitoring-batch-topic", testDto);
        }
    }

    @KafkaListener(topics = "monitoring-single-topic", groupId = "monitoring-group", containerFactory = "monitoringSingleKafkaListenerContainerFactory")
    public void consumeSingleTest(TestDto payload) {

        log.error("[SINGLE LISTENER]: {}", payload.getCount());

        if (singleTestCounter.decrementAndGet() == 0) {
            long singleTestEndTime = System.currentTimeMillis();
            log.info("[싱글리스너]: {} ms", (singleTestEndTime - singleTestStartTime));
        }
    }

    @KafkaListener(topics = "monitoring-batch-topic", groupId = "monitoring-group", containerFactory = "monitoringBatchKafkaListenerContainerFactory")
    public void consumeBatchTest(List<TestDto> payloads) {

        payloads.forEach(payload -> log.error("[BATCH LISTENER]: {}", payload.getCount()));

        long batchTestEndTime = System.currentTimeMillis();
        log.info("[배치리스너]: {} ms", (batchTestEndTime - batchTestStartTime));
    }

}
