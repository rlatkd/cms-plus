package kr.or.kosa.cmsplusmain.domain.settings.dto;

import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import kr.or.kosa.cmsplusmain.domain.product.entity.ProductStatus;
import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private int price;
    private ProductStatus status;

    public static ProductDto fromEntity(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStatus(product.getStatus());
        return dto;
    }
}
