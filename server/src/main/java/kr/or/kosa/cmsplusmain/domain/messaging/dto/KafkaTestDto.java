package kr.or.kosa.cmsplusmain.domain.messaging.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class KafkaTestDto {

    private String type;
    private String phone;
    private String text;

}
