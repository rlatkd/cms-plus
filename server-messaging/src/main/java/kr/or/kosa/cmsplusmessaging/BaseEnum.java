package kr.or.kosa.cmsplusmessaging;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface BaseEnum {
    @JsonGetter("code") String getCode();
    @JsonGetter("title") String getTitle();
}
