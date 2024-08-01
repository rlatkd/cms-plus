package kr.or.kosa.cmsplusmain.domain.kafka.controller;

import kr.or.kosa.cmsplusmain.domain.kafka.dto.messaging.EmailMessageDto;
import kr.or.kosa.cmsplusmain.domain.kafka.dto.messaging.SmsMessageDto;
import kr.or.kosa.cmsplusmain.domain.kafka.dto.payment.AccountPaymentDto;
import kr.or.kosa.cmsplusmain.domain.kafka.dto.payment.CardPaymentDto;
import kr.or.kosa.cmsplusmain.domain.kafka.dto.payment.VirtualAccountPaymentDto;
import kr.or.kosa.cmsplusmain.domain.kafka.service.KafkaMessagingService;
import kr.or.kosa.cmsplusmain.domain.kafka.service.KafkaPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

// @RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/kafka")
public class KafkaController {

    private final KafkaMessagingService kafkaMessagingService;
    private final KafkaPaymentService kafkaPaymentService;

    @PostMapping("/messaging/sms")
    public void sendSms(@RequestBody SmsMessageDto smsMessageDto) {
        kafkaMessagingService.produceMessaging(smsMessageDto);
    }

    @PostMapping("/messaging/email")
    public void sendEmail(@RequestBody EmailMessageDto emailMessageDto) {
        kafkaMessagingService.produceMessaging(emailMessageDto);
    }

    @PostMapping("/messaging/simpconsent")
    public void sendSimpConsent(@RequestBody SmsMessageDto smsMessageDto) {
        kafkaMessagingService.produceMessaging(smsMessageDto);
    }

    @PostMapping("/payment/card")
    public void getCardPaymentResult(@RequestBody CardPaymentDto cardPaymentDto) {
        kafkaPaymentService.producePayment(cardPaymentDto);
    }

    @PostMapping("/payment/account")
    public void getAccountPaymentResult(@RequestBody AccountPaymentDto accountPaymentDto) {
        kafkaPaymentService.producePayment(accountPaymentDto);
    }

    @PostMapping("/payment/virtual-account")
    public void getVirtualAccountPaymentResult(@RequestBody VirtualAccountPaymentDto virtualAccountPaymentDto) {
        kafkaPaymentService.producePayment(virtualAccountPaymentDto);
    }

}
