package kr.or.kosa.cmsplusmain.domain.product.service;

import kr.or.kosa.cmsplusmain.domain.product.dto.ProductDetail;
import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import kr.or.kosa.cmsplusmain.domain.product.repository.ProductCustomRepository;
import kr.or.kosa.cmsplusmain.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCustomRepository productCustomRepository;

    public ProductDetail findById(Long productId) {
        int contractNum = productCustomRepository
                .getContractNumber(productId);

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return ProductDetail.fromEntity(product, contractNum);

    }

}
