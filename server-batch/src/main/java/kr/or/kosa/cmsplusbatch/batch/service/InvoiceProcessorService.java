package kr.or.kosa.cmsplusbatch.batch.service;

import kr.or.kosa.cmsplusbatch.batch.dto.MessageDto;
import kr.or.kosa.cmsplusbatch.batch.dto.SmsMessageDto;
import kr.or.kosa.cmsplusbatch.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusbatch.util.FormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class InvoiceProcessorService implements ItemProcessor<Billing, Billing> {

    // @RequiredArgsConstructor로 하면 BatchConifg에서 에러남
    @Autowired
    KafkaMessagingService kafkaMessagingService;

    private static final String INVOICE_URL_FORMAT = "https://localhost:8080/invoice/%d";
    private static final String INVOICE_MESSAGE_FORMAT =
            """
            %s님의 청구서가 도착했습니다.
            
            - 청구서명: %s
            - 납부할금액: %s원
            - 납부 기한: %s
            
            납부하기: %s
            """.trim();

    private String createInvoiceMessage(Billing billing) {
        String memberName = billing.getContract().getMember().getName();
        String invoiceName = billing.getInvoiceName();
        String billingPrice = FormatUtil.formatMoney(billing.getBillingPrice());
        String billingDate = billing.getBillingDate().toString();
        String url = INVOICE_URL_FORMAT.formatted(billing.getId());
        return INVOICE_MESSAGE_FORMAT
                .formatted(memberName, invoiceName, billingPrice, billingDate, url)
                .trim();
    }

    @Override
    public Billing process(Billing billing) throws Exception {
        sendInvoice(billing);
        return billing;
    }

    @Transactional
    public void sendInvoice(Billing billing) {
        // TODO 청구서 발송 로직 구현
        billing.setInvoiceSent();
        log.error("청구서 발송 완료: {}", billing.getId());
        String text = createInvoiceMessage(billing);
        String phoneNumber = billing.getContract().getMember().getPhone();
        MessageDto messageDto = new SmsMessageDto(text, phoneNumber);
        kafkaMessagingService.produceMessaging(messageDto);
    }

}
