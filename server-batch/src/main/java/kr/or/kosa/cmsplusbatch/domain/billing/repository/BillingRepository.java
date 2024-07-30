package kr.or.kosa.cmsplusbatch.domain.billing.repository;

import kr.or.kosa.cmsplusbatch.domain.base.repository.BaseRepository;
import kr.or.kosa.cmsplusbatch.domain.billing.entity.Billing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillingRepository extends BaseRepository<Billing, Long> {
    @Query("SELECT b FROM Billing b WHERE b.billingDate = :billingDate AND b.contract.member.autoInvoiceSend = true AND b.deleted = false")
    Page<Billing> findAllByBillingDateAndMemberInvoiceSendMethod(@Param("billingDate") LocalDate billingDate, Pageable pageable);

}
