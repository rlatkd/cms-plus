package kr.or.kosa.cmsplusmain.test.domain.products.entity;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TProducts {

    private String name;
    private double price;
    private int contractCount;
    private String createdAt;
    private String notes;

}