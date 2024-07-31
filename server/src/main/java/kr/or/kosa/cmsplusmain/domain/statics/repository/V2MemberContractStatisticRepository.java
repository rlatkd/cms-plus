package kr.or.kosa.cmsplusmain.domain.statics.repository;

import com.querydsl.core.types.dsl.Expressions;
import kr.or.kosa.cmsplusmain.domain.base.repository.V2BaseRepository;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.statics.dto.MemberContractStatisticDto;
import kr.or.kosa.cmsplusmain.domain.statics.dto.QMemberContractStatisticDto;

import java.time.LocalDate;
import java.util.List;

import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.contract;
import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.member;
import static kr.or.kosa.cmsplusmain.domain.payment.entity.QPayment.payment;

public class V2MemberContractStatisticRepository extends V2BaseRepository<Member, Long> {

    public List<MemberContractStatisticDto> findMemberContractStatistic(Long vendorId) {
        return selectWithNotDel(new QMemberContractStatisticDto(
                member.id, member.name, member.enrollDate,
                Expressions.stringTemplate("DATEDIFF(c.contract_end_date, c.contract_start_date) as contract_duration").castToNum(Integer.class),
                contract.contractPrice,
                payment.paymentMethod,
                contract.contractStartDate.between(
                        LocalDate.now().withDayOfMonth(1),
                        LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1)
                )), member, contract)
                .from(contract)
                .join(contract.member, member)
                .join(contract.payment, payment)
                .fetch();
    }
}
