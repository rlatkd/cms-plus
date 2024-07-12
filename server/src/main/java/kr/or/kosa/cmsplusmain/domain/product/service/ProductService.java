package kr.or.kosa.cmsplusmain.domain.product.service;

import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
import kr.or.kosa.cmsplusmain.domain.product.dto.*;
import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import kr.or.kosa.cmsplusmain.domain.product.repository.ProductCustomRepository;
import kr.or.kosa.cmsplusmain.domain.product.repository.ProductRepository;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCustomRepository productCustomRepository;

    /*
    * TODO 상품 생성
    *  repository
    *  service
    *  controller
    *  exception - 이전꺼 포함
    *  validation - 이전꺼 포함
    * */
    @Transactional
    public void createProduct(Long vendorId, ProductCreateReq productCreateReq) {
        productRepository.save(productCreateReq.toEntity(Vendor.builder().id(vendorId).build()));
    }

    /*
     * 상품 목록 조회
     * */
    public PageRes<ProductListItemRes> searchProducts(String vendorUserName, ProductSearchReq search, PageReq pageable) {
        List<ProductListItemRes> content = productCustomRepository.findProductListWithCondition(vendorUserName, search, pageable)
                .stream()
                .map(ProductQueryDto::toProductRes)
                .toList();

        int totalContentCount = productCustomRepository.countAllProducts(vendorUserName);

        return new PageRes<>(totalContentCount, pageable.getSize(), content);
    }

    /*
     * 상품 상세 조회
     * */
    public ProductDetailRes getProductDetail(Long productId, String vendorUserName) {
        // 해당 상품들 주인이 맞는지 검증
        validateProductUser(productId, vendorUserName);

        // 계약 건수
        int contractNum = productCustomRepository
                .getContractNumber(productId);

        // 상품 정보(계약건수를 제외한)
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // 계약 건수 + 상품 정보
        return ProductDetailRes.fromEntity(product, contractNum);
    }

    // 유효성 검증
    private void validateProductUser(Long productId, String vendorUserName) {
        if (!productCustomRepository.isExistProductByUsername(productId, vendorUserName)) {
            throw new IllegalArgumentException("Not Owner");
        }
    }

}