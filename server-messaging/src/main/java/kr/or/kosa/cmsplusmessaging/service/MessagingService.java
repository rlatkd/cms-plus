package kr.or.kosa.cmsplusmessaging.service;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kr.or.kosa.cmsplusmessaging.dto.EmailMessageDto;
import kr.or.kosa.cmsplusmessaging.dto.MessageDto;
import kr.or.kosa.cmsplusmessaging.dto.SmsMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessagingService {

    @Value("${sms.phone}")
    private String smsPhone;

    private final DefaultMessageService messageService; // coolsms

    // 컨트롤러에서 호출할 메서드
//    public void sendSms(List<MessageDto> messages) {
//        ArrayList<Message> messageList = new ArrayList<>();
//
//        for (MessageDto messageDto : messages) {
//            Message message = new Message();
//            message.setFrom(smsPhone); // 발신번호(내 번호)
//            message.setTo(messageDto.getPhoneNumber()); // 수신번호
//            message.setText(messageDto.getText()); // 수신내용
//            messageList.add(message);
//        }
//
//        sendMany(messageList);
//    }













    @Value("${email.emailAddress}")
    private String emailAddress;

    // 이메일 대량은 한 번에 100건, 하루 500건
    private final JavaMailSender mailSender;

    public String sendEmail(EmailMessageDto emailMessageDto) throws MailException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setTo(emailMessageDto.getEmailAddress()); // 메일 보낼 곳
            helper.setFrom(emailAddress); // 메일 보내는 곳
            helper.setSubject("보낼 이메일 제목"); // 메일 제목
            helper.setText(emailMessageDto.getText()); // 메일 내용
            mailSender.send(message); //
            return "[email 전송 성공]";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "[email 전송 실패]";
        }

    }

    public SingleMessageSentResponse sendSms(SmsMessageDto smsMessageDto) {

            Message message = new Message();
            message.setFrom(smsPhone);
            message.setTo(smsMessageDto.getPhoneNumber());
            message.setText(smsMessageDto.getText());
            SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));

            return response;



    }



    // 대량 sms용 메서드(쿨에스엠에스에서 제공)
    private MultipleDetailMessageSentResponse sendMany(ArrayList<Message> messageList) {
        try {
            MultipleDetailMessageSentResponse response = this.messageService.send(messageList, true);
            return response;
        } catch (NurigoMessageNotReceivedException e) {
            log.error(e.getFailedMessageList().toString());
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }








    @KafkaListener(topics = "messaging-topic", groupId = "messaging-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumeMessage(ConsumerRecord<String, MessageDto> consumerRecord) {
        MessageDto messageDto = consumerRecord.value();
        switch (messageDto.getMethod()) {
            case SMS -> handleSmsMessage((SmsMessageDto) messageDto);
            case EMAIL -> handleEmailMessage((EmailMessageDto) messageDto);
        }
    }

    private void handleSmsMessage(SmsMessageDto smsMessageDto) {
        log.error("[SMS 메시지 소비됨]: {}", smsMessageDto.toString());
       sendSms(smsMessageDto);
    }

    private void handleEmailMessage(EmailMessageDto emailMessageDto) {
        log.error("[EMAIL 메시지 소비됨]: {}", emailMessageDto.toString());
        sendEmail(emailMessageDto);
    }

}
