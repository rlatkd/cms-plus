package kr.or.kosa.cmsplusmain.domain.product.controller;

import java.util.List;

import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
import kr.or.kosa.cmsplusmain.domain.base.security.VendorId;
import kr.or.kosa.cmsplusmain.domain.product.dto.*;
import kr.or.kosa.cmsplusmain.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vendor/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public void createProduct(@VendorId Long vendorId, @RequestBody ProductCreateReq product) {
//        Long vendorId = 1L;
        productService.createProduct(vendorId, product);
    }

    @GetMapping("/{productId}")
    public ProductDetailRes getProduct(@VendorId Long vendorId, @PathVariable("productId") Long productId) {
//        Long vendorId = 1L; // 고객1 더미데이터
        return productService.getProductDetail(productId, vendorId);
    }

    /*
    * 고객의 전체 상품 목록 (조건 없이, 이름과 수량 그리고 ID 만)
    * */
    @GetMapping("/all")
    public List<ProductDto> getAllProducts(@VendorId Long vendorId) {
//        Long vendorId = 1L;
        return productService.getAllProducts(vendorId);
    }

    @GetMapping
    public PageRes<ProductListItemRes> getProductListItem(@VendorId Long vendorId, ProductSearchReq search, PageReq pageable) { // 검색 및 필터링 조건DTO, 페이지네이션DTO
//        Long vendorId = 1L;
        return productService.searchProducts(vendorId, search, pageable);
    }

    @PutMapping("/{productId}")
    public void updateProduct(@VendorId Long vendorId, @PathVariable("productId") Long productId, @RequestBody ProductUpdateReq productUpdateReq) {
//        Long vendorId = 1L;
        productService.updateProduct(vendorId, productId, productUpdateReq);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@VendorId Long vendorId, @PathVariable("productId") Long productId) {
//        Long vendorId = 1L;
        productService.deleteProduct(vendorId, productId);
    }
}
