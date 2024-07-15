package kr.or.kosa.cmsplusmain.domain.member.repository;

import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.*;
import static kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseCustomRepository;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public class MemberCustomRepository extends BaseCustomRepository<Member> {
    public MemberCustomRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
        super(em, jpaQueryFactory);
    }

    /*
    * 회원 목록 조회
    * */
    public List<Member> findAllMemberByVendor(Long vendorId, SortPageDto.Req pageable) {
        return jpaQueryFactory
            .selectFrom(member)
            .where(
                    member.vendor.id.eq(vendorId),
                    memberNotDel()
            )
            .offset(pageable.getPage())
            .limit(pageable.getSize())
            .fetch();
    }

    /*
     * 전체 회원 수
     * */
    public int countAllMemberByVendor(Long vendorId) {
        return jpaQueryFactory
            .select(member.id.countDistinct()).from(member)
            .where(
                    member.vendor.id.eq(vendorId),
                    memberNotDel()
            )
            .fetchOne().intValue();
    }

    /*
    * 회원 상세 조회 : 기본정보
    *
    *   select
            member1
        from
            Member member1
        inner join
            member1.vendor as vendor
        where
            vendor.username = ?1
            and member1.id = ?2
            and member1.deleted = ?3
    * */
    public Optional<Member> findMemberDetailById(Long vendorId, Long memberId){
        return Optional.ofNullable(
            jpaQueryFactory
            .selectFrom(member)
            .where(
                    member.vendor.id.eq(vendorId),
                    member.id.eq(memberId),
                    memberNotDel()
            )
            .fetchOne());
    }

    /*
     * 회원 존재 여부 판단
     * */
    public boolean idExistMemberByPhone(Long vendorId, String phone) {
        Integer fetchOne = jpaQueryFactory
            .selectOne()
            .from(member)
            .where(
                    member.vendor.id.eq(vendorId),
                    member.phone.eq(phone),
                    memberNotDel()
            )
            .fetchFirst();

        return fetchOne != null;
    }

    public Optional<Member> findMemberByPhone(Long vendorId, String phone) {
        return Optional.ofNullable(
            jpaQueryFactory
                .selectFrom(member)
                .where(
                        member.vendor.id.eq(vendorId),
                        member.phone.eq(phone),
                        memberNotDel()
                )
                .fetchOne());
    }
}