package kr.or.kosa.cmsplusmain.domain.vendor.dto.Identifier;

import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.base.validator.Phone;
import kr.or.kosa.cmsplusmain.domain.kafka.MessageSendMethod;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SmsIdFindReq extends IdFindReq{

    @NotNull
    @Phone
    private String phone;

    public SmsIdFindReq(String authenticationNumber, String name, String phone) {
        super(MessageSendMethod.SMS, name, authenticationNumber);
        this.phone = phone;
    }
}
