package kr.or.kosa.cmsplusbatch.batch.dto;

import kr.or.kosa.cmsplusbatch.batch.MessageSendMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class SmsMessageDto extends MessageDto {

    private String phoneNumber;

    public SmsMessageDto(String text, String phoneNumber) {
        super(MessageSendMethod.SMS, text);
        this.phoneNumber = phoneNumber;
    }

}
