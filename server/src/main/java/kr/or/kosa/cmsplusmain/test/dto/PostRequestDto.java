package kr.or.kosa.cmsplusmain.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostRequestDto {
    private String name;
    private String content;
}