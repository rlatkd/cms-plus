//package kr.or.kosa.cmsplusbatch.batch.service;
//
//import kr.or.kosa.cmsplusbatch.batch.dto.BillingQueryDto;
//import kr.or.kosa.cmsplusbatch.batch.service.KafkaMessagingService;
//import kr.or.kosa.cmsplusbatch.util.FormatUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class V2InvoiceProcessorService implements ItemProcessor<BillingQueryDto, BillingQueryDto> {
//
//    @Autowired
//    KafkaMessagingService kafkaMessagingService;
//
//    private static final String INVOICE_URL_FORMAT = "https://localhost:8080/invoice/%d";
//    private static final String INVOICE_MESSAGE_FORMAT =
//            """
//            %s님의 청구서가 도착했습니다.
//
//            - 청구서명: %s
//            - 납부할금액: %s원
//            - 납부 기한: %s
//
//            납부하기: %s
//            """.trim();
//
//    private String createInvoiceMessage(BillingQueryDto billing) {
//        String memberName = billing.getMemberName();
//        String invoiceName = billing.getInvoiceName();
//        String billingPrice = FormatUtil.formatMoney(billing.getTotalBillingProductPrice());
//        String billingDate = billing.getBillingDate().toString();
//        String url = String.format(INVOICE_URL_FORMAT, billing.getId());
//        return String.format(INVOICE_MESSAGE_FORMAT, memberName, invoiceName, billingPrice, billingDate, url).trim();
//    }
//
//    @Override
//    public BillingQueryDto process(BillingQueryDto billing) throws Exception {
//        sendInvoice(billing);
//        return billing;
//    }
//
//    @Transactional
//    public void sendInvoice(BillingQueryDto billing) {
//        billing.setInvoiceSent();
//        // log.info("청구서 발송 완료: {}", billing.getId());
//        // String text = createInvoiceMessage(billing);
//        // String phoneNumber = billing.getMemberPhone();
//        // MessageDto messageDto = new SmsMessageDto(text, phoneNumber);
//        // kafkaMessagingService.produceMessaging(messageDto);
//    }
//}
