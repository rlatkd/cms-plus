package kr.or.kosa.cmsplusmain.domain.kafka.dto.messaging;


import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.kafka.MessageSendMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SmsMessageDto extends MessageDto {

    @NotNull
    private String phoneNumber;

    public SmsMessageDto(String text, String phoneNumber) {
        super(MessageSendMethod.SMS, text);
        this.phoneNumber = phoneNumber;
    }

}
