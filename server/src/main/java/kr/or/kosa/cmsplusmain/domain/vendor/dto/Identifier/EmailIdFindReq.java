package kr.or.kosa.cmsplusmain.domain.vendor.dto.Identifier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.kafka.MessageSendMethod;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailIdFindReq extends IdFindReq{

    @NotNull
    @Email
    private String email;

    public EmailIdFindReq(String authenticationNumber, String name, String email) {
        super(MessageSendMethod.EMAIL, name, authenticationNumber);
        this.email = email;
    }
}
