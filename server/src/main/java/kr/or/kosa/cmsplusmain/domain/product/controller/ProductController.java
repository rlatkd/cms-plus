package kr.or.kosa.cmsplusmain.domain.product.controller;

import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
import kr.or.kosa.cmsplusmain.domain.product.dto.ProductCreateReq;
import kr.or.kosa.cmsplusmain.domain.product.dto.ProductDetailRes;
import kr.or.kosa.cmsplusmain.domain.product.dto.ProductListItemRes;
import kr.or.kosa.cmsplusmain.domain.product.dto.ProductSearchReq;
import kr.or.kosa.cmsplusmain.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendor/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ProductDetailRes getProduct(@PathVariable("productId") Long productId) {
        String vendorUserName = "vendor1"; // 고객1 더미데이터
        return productService.getProductDetail(productId, vendorUserName);
    }

    @GetMapping
    public PageRes<ProductListItemRes> getProductListItem(ProductSearchReq search, PageReq pageable) { // 검색 및 필터링 조건DTO, 페이지네이션DTO
        String vendorUserName = "vendor1";
        return productService.searchProducts(vendorUserName, search, pageable);
    }

    @PostMapping
    public void createProduct(@RequestBody ProductCreateReq product) {
        Long vendorId = 1L;
        log.error("11");
        productService.createProduct(vendorId, product);

    }

}
