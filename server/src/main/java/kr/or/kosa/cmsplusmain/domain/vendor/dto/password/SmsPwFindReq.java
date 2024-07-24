package kr.or.kosa.cmsplusmain.domain.vendor.dto.password;

import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.base.validator.Phone;
import kr.or.kosa.cmsplusmain.domain.kafka.MessageSendMethod;
import lombok.Getter;

@Getter
public class SmsPwFindReq extends PwFindReq{

    @NotNull
    @Phone
    private String phone;

    public SmsPwFindReq(String authenticationNumber, String username, String phone) {
        super(MessageSendMethod.SMS, username, authenticationNumber);
        this.phone = phone;
    }
}
