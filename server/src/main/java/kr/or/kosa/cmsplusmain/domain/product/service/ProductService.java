package kr.or.kosa.cmsplusmain.domain.product.service;

import com.querydsl.core.Tuple;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.or.kosa.cmsplusmain.domain.product.dto.ProductRes;
import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import kr.or.kosa.cmsplusmain.domain.product.repository.ProductCustomRepository;
import kr.or.kosa.cmsplusmain.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.stream.Collectors;

import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContractProduct.contractProduct;
import static kr.or.kosa.cmsplusmain.domain.product.entity.QProduct.product;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCustomRepository productCustomRepository;

    public ProductRes findById(Long productId) {
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

    public SortPageDto.Res<ProductRes> findProductsByUser(String vendorUserName, SortPageDto.Req pageable) {
        int countProductListItem = productCustomRepository.countAllProducts(vendorUserName);
        int totalPages = (int) Math.ceil((double) countProductListItem / pageable.getSize());
        List<Tuple> productListTuples = productCustomRepository.findProductListWithCondition(vendorUserName, pageable);

        List<ProductRes> productList = productListTuples
                .stream()
                .map(tuple -> ProductRes.fromEntity(
                        tuple.get(product), // 튜플 stream
                        tuple.get(contractProduct.contract.countDistinct()).intValue()
                ))
                .collect(Collectors.toList());

        return new SortPageDto.Res<>(totalPages, productList);
    }

    public void validateProductUser(Long productId, String vendorUserName) {
        if (!productCustomRepository.isExistProductByUsername(productId, vendorUserName)) {
            throw new IllegalArgumentException("Not Owner");
        }
    }

}