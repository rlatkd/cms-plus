package kr.or.kosa.cmsplusbatch.domain.billing.repository;

import kr.or.kosa.cmsplusbatch.domain.base.repository.BaseRepository;
import kr.or.kosa.cmsplusbatch.domain.billing.entity.Billing;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends BaseRepository<Billing, Long> {
}
