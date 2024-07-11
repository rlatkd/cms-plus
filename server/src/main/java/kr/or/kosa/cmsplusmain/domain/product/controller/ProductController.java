package kr.or.kosa.cmsplusmain.domain.product.controller;

import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.product.dto.ProductRes;
import kr.or.kosa.cmsplusmain.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vendor/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ProductRes getProduct(@PathVariable("productId") Long productId) {
        String vendorUserName = "vendor1";
        return productService.findById(productId, vendorUserName);
    }

    @GetMapping
    public SortPageDto.Res<ProductRes> getProductListItem(SortPageDto.Req pageable) {
        String vendorUserName = "vendor1";
        return productService.findProductsByUser(vendorUserName, pageable);
    }

}
