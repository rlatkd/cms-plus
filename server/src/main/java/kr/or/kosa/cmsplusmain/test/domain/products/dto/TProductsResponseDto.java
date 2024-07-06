package kr.or.kosa.cmsplusmain.test.domain.products.dto;

import kr.or.kosa.cmsplusmain.test.domain.products.entity.TProducts;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TProductsResponseDto {

    private String name;
    private double price;
    private int contractCount;
    private String createdAt;
    private String notes;

    public static TProductsResponseDto fromEntity(TProducts tProducts) {
        return TProductsResponseDto.builder()
                .name(tProducts.getName())
                .contractCount(tProducts.getContractCount())
                .price(tProducts.getPrice())
                .createdAt(tProducts.getCreatedAt())
                .notes(tProducts.getNotes())
                .build();
    }

}