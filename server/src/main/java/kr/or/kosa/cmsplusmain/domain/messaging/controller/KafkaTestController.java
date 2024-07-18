package kr.or.kosa.cmsplusmain.domain.messaging.controller;

import kr.or.kosa.cmsplusmain.domain.messaging.dto.EmailMessageDto;
import kr.or.kosa.cmsplusmain.domain.messaging.dto.SmsMessageDto;
import kr.or.kosa.cmsplusmain.domain.messaging.dto.TestDto;
import kr.or.kosa.cmsplusmain.domain.messaging.service.MessagingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/kafkatest")
public class KafkaTestController {

    private final MessagingService messagingService;
    String messagingTopic = "messaging-topic";
    String paymentTopic = "payment-topic";

    @PostMapping("/sms")
    public String sendSms(@RequestBody SmsMessageDto smsMessageDto) {
        messagingService.send(messagingTopic, smsMessageDto);
        return "success sms";
    }

    @PostMapping("/email")
    public String sendEmail(@RequestBody EmailMessageDto emailMessageDto) {
        messagingService.send(messagingTopic, emailMessageDto);
        return "success email";
    }


    // 테스트
    @PostMapping("/payment")
    public void test(@RequestBody TestDto testDto) {
        messagingService.test(paymentTopic, testDto);
        log.error("[결제 시작]: {}", testDto.toString());
    }



}
