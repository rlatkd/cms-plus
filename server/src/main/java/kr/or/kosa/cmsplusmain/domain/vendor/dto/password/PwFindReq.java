package kr.or.kosa.cmsplusmain.domain.vendor.dto.password;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.base.validator.Username;
import kr.or.kosa.cmsplusmain.domain.kafka.MessageSendMethod;
import lombok.Getter;

@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "method"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SmsPwFindReq.class, name = "SMS"),
        @JsonSubTypes.Type(value = EmailPwFindReq.class, name = "EMAIL")
})

public abstract class PwFindReq {

    @NotNull
    private MessageSendMethod method;

    @NotNull
    @Username
    private String username ;

    @NotNull
    private String authenticationNumber;

    public PwFindReq(MessageSendMethod method, String username, String authenticationNumber){
        this.method = method;
        this.username = username;
        this.authenticationNumber = authenticationNumber;
    }
}
