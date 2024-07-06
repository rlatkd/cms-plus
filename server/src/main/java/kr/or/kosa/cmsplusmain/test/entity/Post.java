package kr.or.kosa.cmsplusmain.test.entity;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    private String name;
    private String content;
}