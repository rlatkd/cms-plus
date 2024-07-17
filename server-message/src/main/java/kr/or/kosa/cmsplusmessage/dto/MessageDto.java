package kr.or.kosa.cmsplusmessage.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageDto {

    private String type;
    private String phoneNumber;
    private String emailAddress;
    private String text;

}
