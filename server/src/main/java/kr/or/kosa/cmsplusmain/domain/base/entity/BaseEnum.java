package kr.or.kosa.cmsplusmain.domain.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface BaseEnum {
	@JsonGetter("code") String getCode();
	@JsonGetter("title") String getTitle();
}
