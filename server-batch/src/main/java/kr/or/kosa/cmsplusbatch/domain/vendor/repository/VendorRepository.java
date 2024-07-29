package kr.or.kosa.cmsplusbatch.domain.vendor.repository;

import kr.or.kosa.cmsplusbatch.domain.base.repository.BaseRepository;
import kr.or.kosa.cmsplusbatch.domain.vendor.entity.Vendor;
import org.springframework.stereotype.Repository;


@Repository
public interface VendorRepository extends BaseRepository<Vendor, Long> {
}
