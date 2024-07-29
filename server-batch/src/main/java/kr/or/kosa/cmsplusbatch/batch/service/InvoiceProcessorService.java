package kr.or.kosa.cmsplusbatch.batch.service;

import kr.or.kosa.cmsplusbatch.batch.dto.MessageDto;
import kr.or.kosa.cmsplusbatch.batch.dto.SmsMessageDto;
import kr.or.kosa.cmsplusbatch.domain.billing.entity.Billing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class InvoiceProcessor implements ItemProcessor<Billing, Billing> {

    // @RequiredArgsConstructor로 하면 BatchConifg에서 에러남
    @Autowired
    KafkaMessagingService kafkaMessagingService;

    @Override
    public Billing process(Billing billing) throws Exception {
        sendInvoice(billing);
        return billing;
    }

    @Transactional
    protected void sendInvoice(Billing billing) {
        // TODO 청구서 발송 로직 구현
        billing.setInvoiceSent();
        log.error("청구서 발송 완료: {}", billing.getId());
        String text = "배치 sms";
        String phoneNumber = billing.getContract().getMember().getPhone();
        MessageDto messageDto = new SmsMessageDto(text, phoneNumber);
        kafkaMessagingService.produceMessaging(messageDto);
    }

}


