package kr.or.kosa.cmsplusmain.domain.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.kosa.cmsplusmain.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    // public ProductDetail findById(Long productId) {
    //     Product product = productRepository
    //             .findById(productId)
    //             .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    //
    //     return ProductDetail.fromEntity(product);
    //
    // }
}
