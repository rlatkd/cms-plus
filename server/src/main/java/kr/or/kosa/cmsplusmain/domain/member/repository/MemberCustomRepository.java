package kr.or.kosa.cmsplusmain.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseCustomRepository;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.member;
import static kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor.vendor;

@Repository
public class MemberCustomRepository extends BaseCustomRepository<Member> {
    public MemberCustomRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
        super(em, jpaQueryFactory);
    }

    public int countAllByVendorUsername(String Username) {
        return jpaQueryFactory
                .select(member.id.countDistinct()).from(member)
                .join(member.vendor, vendor)
                .where(
                        vendorUsernameEq(Username),
                        memberNotDel()
                )
                .fetchOne().intValue();
    }

    public List<Member> findPagedByVendorUsername(String Username, SortPageDto.Req pageable) {
        return jpaQueryFactory
                .selectFrom(member)
                .join(member.vendor, vendor)
                .where(
                        vendorUsernameEq(Username),
                        memberNotDel()
                )
                .offset(pageable.getPage())
                .limit(pageable.getSize())
                .fetch();
    }
}
