package kr.or.kosa.cmsplusmain.domain.kafka.dto.messaging;

import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.kafka.MessageSendMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class MessageDto { // sms, email은 이걸 상속해서 각자 타입에 맞는 핸드폰번호, email주소를 주입해야함

    @NotNull
    private String method;

    @NotNull
    private String text;

    public MessageDto(MessageSendMethod method, String text) {
        this.method = method.getCode();
        this.text = text;
    }

}
