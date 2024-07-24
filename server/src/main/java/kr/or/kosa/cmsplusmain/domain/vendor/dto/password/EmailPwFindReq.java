package kr.or.kosa.cmsplusmain.domain.vendor.dto.password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.kafka.MessageSendMethod;
import lombok.Getter;

@Getter
public class EmailPwFindReq extends PwFindReq{

    @NotNull
    @Email
    private String email;

    public EmailPwFindReq(String authenticationNumber, String username, String email) {
        super(MessageSendMethod.EMAIL, username, authenticationNumber);
        this.email = email;
    }
}
