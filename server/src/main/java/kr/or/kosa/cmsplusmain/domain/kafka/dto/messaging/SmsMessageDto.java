package kr.or.kosa.cmsplusmain.domain.kafka.dto.messaging;


import kr.or.kosa.cmsplusmain.domain.kafka.MessageSendMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SmsMessageDto extends MessageDto {

    private String phoneNumber;

    public SmsMessageDto(String text, String phoneNumber) {
        super(MessageSendMethod.SMS, text);
        this.phoneNumber = phoneNumber;
    }

}
