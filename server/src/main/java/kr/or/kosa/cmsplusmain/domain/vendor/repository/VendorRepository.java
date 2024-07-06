package kr.or.kosa.cmsplusmain.domain.vendor.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseRepository;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;
import org.springframework.stereotype.Repository;

import static kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor.vendor;

@Repository
public class VendorRepository extends BaseRepository<Vendor, Long> {

    public VendorRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
        super(em, jpaQueryFactory);
    }

    /*
    * 이미 존재하는 아이디인 경우: true
    * */
    public boolean isExistUsername(String username) {
        Integer count = jpaQueryFactory
                .selectOne()
                .from(vendor)
                .where(vendor.username.eq(username), vendor.deleted.eq(false))
                .fetchFirst();

        return count != null;
    }
}
