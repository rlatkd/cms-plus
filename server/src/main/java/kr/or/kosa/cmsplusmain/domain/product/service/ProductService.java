package kr.or.kosa.cmsplusmain.domain.product.service;

import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.product.dto.ProductRes;
import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import kr.or.kosa.cmsplusmain.domain.product.repository.ProductCustomRepository;
import kr.or.kosa.cmsplusmain.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCustomRepository productCustomRepository;

    // 상품 상세 조회
    public ProductRes findById(Long productId, String vendorUserName) {

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
        return ProductRes.fromEntity(product, contractNum);

    }

    // 상품 목록 조회
    public SortPageDto.Res<ProductRes> findProductsByUser(String vendorUserName, SortPageDto.Req pageable) {

        int countProductListItem = productCustomRepository.countAllProducts(vendorUserName);
        int totalPages = (int) Math.ceil((double) countProductListItem / pageable.getSize());

        List<ProductRes> productList = productCustomRepository.findProductListWithCondition(vendorUserName, pageable)
                .stream()
                .map(pqr -> pqr.toProductRes())
                .toList();

        return new SortPageDto.Res<>(totalPages, productList);

    }

    // 유효성 검증
    public void validateProductUser(Long productId, String vendorUserName) {
        if (!productCustomRepository.isExistProductByUsername(productId, vendorUserName)) {
            throw new IllegalArgumentException("Not Owner");
        }
    }


    public List<ProductRes> findAvailableProductsByVendorUsername(String username) {
        return productCustomRepository.findAvailableProductsByVendorUsername(username)
                .stream()
                .map(product -> ProductRes.fromEntity(product, productCustomRepository.getContractNumber(product.getId())))
                .collect(Collectors.toList());
    }

}