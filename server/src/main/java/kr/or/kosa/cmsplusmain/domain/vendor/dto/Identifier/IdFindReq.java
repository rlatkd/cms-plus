package kr.or.kosa.cmsplusmain.domain.vendor.dto.Identifier;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.kafka.MessageSendMethod;
import lombok.Getter;


@Getter
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "method"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = SmsIdFindReq.class, name = "SMS"),
    @JsonSubTypes.Type(value = EmailIdFindReq.class, name = "EMAIL")
})
public abstract class IdFindReq {

    @NotNull
    private MessageSendMethod method;

    @NotNull
    private String name;

    @NotNull
    private String authenticationNumber;

    public IdFindReq(MessageSendMethod method, String name, String authenticationNumber){
        this.method = method;
        this.name = name;
        this.authenticationNumber = authenticationNumber;
    }

}
