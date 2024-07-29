package kr.or.kosa.cmsplusbatch.global;

import kr.or.kosa.cmsplusbatch.domain.billing.entity.Billing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InvoiceProcessor implements ItemProcessor<Billing, Billing> {

    @Override
    public Billing process(Billing billing) throws Exception {
        sendInvoice(billing);
        return billing;
    }

    private void sendInvoice(Billing billing) {
        // TODO 청구서 발송 로직 구현
        billing.setInvoiceSent();
        log.error("청구서 발송 완료: {}", billing.getId());

    }

}


