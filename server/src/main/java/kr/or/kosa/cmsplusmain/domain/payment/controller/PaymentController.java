package kr.or.kosa.cmsplusmain.domain.payment.controller;

import kr.or.kosa.cmsplusmain.domain.kafka.dto.payment.PaymentResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/test")
@Slf4j
public class PaymentController {

    @PostMapping
    public void paymentResult(PaymentResultDto paymentResult) {
        log.error("[결제 결과]: {}", paymentResult.toString());
    }

}
