package kr.or.kosa.cmsplusmessaging.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.or.kosa.cmsplusmessaging.MessageSendMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailMessageDto extends MessageDto {

    private String emailAddress;

    @JsonCreator
    public EmailMessageDto(@JsonProperty("text") String text, @JsonProperty("emailAddress") String emailAddress) {
        super(MessageSendMethod.EMAIL, text);
        this.emailAddress = emailAddress;
    }

}
