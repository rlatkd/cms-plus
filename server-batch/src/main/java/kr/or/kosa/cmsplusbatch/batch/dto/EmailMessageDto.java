package kr.or.kosa.cmsplusbatch.batch.dto;

import kr.or.kosa.cmsplusbatch.batch.MessageSendMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailMessageDto extends MessageDto {

    private String emailAddress;

    public EmailMessageDto(String text, String emailAddress) {
        super(MessageSendMethod.EMAIL, text);
        this.emailAddress = emailAddress;
    }

}
