package kr.or.kosa.cmsplusmessaging;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageSendMethod {
    SMS("문자"), EMAIL("이메일");

    private final String title;

}