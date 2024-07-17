package kr.or.kosa.cmsplusmessage.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.or.kosa.cmsplusmessage.MessageSendMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SmsMessageDto extends MessageDto {

    private String phoneNumber;

    @JsonCreator
    public SmsMessageDto(@JsonProperty("text") String text, @JsonProperty("phoneNumber") String phoneNumber) {
        super(MessageSendMethod.SMS, text);
        this.phoneNumber = phoneNumber;
    }

}
