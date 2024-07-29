package kr.or.kosa.cmsplusbatch.domain.product.repository;

import kr.or.kosa.cmsplusbatch.domain.base.repository.BaseRepository;
import kr.or.kosa.cmsplusbatch.domain.product.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
}
