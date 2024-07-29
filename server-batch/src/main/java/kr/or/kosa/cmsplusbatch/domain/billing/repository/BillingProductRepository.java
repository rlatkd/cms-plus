package kr.or.kosa.cmsplusbatch.domain.billing.repository;

import kr.or.kosa.cmsplusbatch.domain.base.repository.BaseRepository;
import kr.or.kosa.cmsplusbatch.domain.billing.entity.BillingProduct;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingProductRepository extends BaseRepository<BillingProduct, Long> {
}
