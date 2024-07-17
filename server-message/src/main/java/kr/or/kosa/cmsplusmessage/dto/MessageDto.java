package kr.or.kosa.cmsplusmessage.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import kr.or.kosa.cmsplusmessage.MessageSendMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "method"
//        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SmsMessageDto.class, name = "SMS"),
        @JsonSubTypes.Type(value = EmailMessageDto.class, name = "EMAIL")
})
public abstract class MessageDto {

    private MessageSendMethod method;
    private String text;

    public MessageDto(MessageSendMethod method, String text) {
        this.method = method;
        this.text = text;
    }

//    private String method;
//    private String phoneNumber;
//    private String emailAddress;
//    private String text;

}
