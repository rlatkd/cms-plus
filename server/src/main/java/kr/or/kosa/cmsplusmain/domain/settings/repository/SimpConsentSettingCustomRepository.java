package kr.or.kosa.cmsplusmain.domain.settings.repository;

import static kr.or.kosa.cmsplusmain.domain.settings.entity.QSimpConsentSetting.*;
import static kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseCustomRepository;
import kr.or.kosa.cmsplusmain.domain.settings.entity.SimpConsentSetting;
import org.springframework.stereotype.Repository;


@Repository
public class SimpConsentSettingCustomRepository extends BaseCustomRepository<SimpConsentSetting> {

    public SimpConsentSettingCustomRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
        super(em, jpaQueryFactory);
    }

    public SimpConsentSetting findByVendorUsername(Long vendorId) {
        return jpaQueryFactory
                .selectFrom(simpConsentSetting)
                .where(
                        simpConsentSetting.vendor.id.eq(vendorId),
                        simpConsentSetting.deleted.isFalse()
                )
                .fetchOne();
    }
}