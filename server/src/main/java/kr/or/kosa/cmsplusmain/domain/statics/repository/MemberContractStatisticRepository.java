package kr.or.kosa.cmsplusmain.domain.statics.repository;

import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.statics.dto.MemberContractStatisticDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberContractStatisticRepository extends JpaRepository<Member, Long> {

    @Query("SELECT new kr.or.kosa.cmsplusmain.domain.statics.dto.MemberContractStatisticDto(" +
            "m.id, m.name, m.enrollDate, " +
            "DATEDIFF(c.contractEndDate, c.contractStartDate), " +
            "SUM(cp.price * cp.quantity), " +
            "p.paymentMethod, " +
            "CASE WHEN COUNT(c2) > 0 THEN true ELSE false END) " +
            "FROM Member m " +
            "JOIN Contract c ON m.id = c.member.id " +
            "JOIN ContractProduct cp ON c.id = cp.contract.id " +
            "LEFT JOIN Payment p ON c.payment.id = p.id " +
            "LEFT JOIN Contract c2 ON m.id = c2.member.id AND c2.contractStartDate > c.contractEndDate " +
            "GROUP BY m.id, m.name, m.enrollDate, c.id, c.contractStartDate, c.contractEndDate, p.paymentMethod")
    List<MemberContractStatisticDto> findMemberContractStatistics();
}