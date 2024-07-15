package kr.or.kosa.cmsplusmain.domain.simpconsent.verification.repository;

import kr.or.kosa.cmsplusmain.domain.base.repository.BaseRepository;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.CmsPaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CmsPaymentRepository extends BaseRepository<CmsPaymentMethod, Long> {
}
