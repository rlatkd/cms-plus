package kr.or.kosa.cmsplusmain.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ProductUpdateReq {

    private String name;
    private double price;
    private String memo;

}
