package kr.or.kosa.cmsplusmain.domain.member.repository;

import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.*;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContractProduct.*;
import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.*;
import static kr.or.kosa.cmsplusmain.domain.product.entity.QProduct.product;
import static kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor.*;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseCustomRepository;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberSearchReq;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;



@Repository
public class MemberCustomRepository extends BaseCustomRepository<Member> {
    public MemberCustomRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
        super(em, jpaQueryFactory);
    }

    /*
    * 회원 목록 조회
    *
    * 기본 정렬: 최신 등록된 회원(회원 등록일 기준, not db 생성)
    * */
    public List<Member> findAllMemberByVendor(Long vendorId, MemberSearchReq memberSearch, PageReq pageable) {
        return jpaQueryFactory
            .selectFrom(member)
            .leftJoin(member.contracts, contract).on(contract.deleted.isFalse())
            .leftJoin(contract.contractProducts, contractProduct).on(contractProduct.deleted.isFalse())
            .where(
                member.vendor.id.eq(vendorId),
                memberNotDel(),

                memberNameContains(memberSearch.getMemberName()),
                memberPhoneContains(memberSearch.getMemberPhone()),
                memberEmailContains(memberSearch.getMemberEmail()),
                memberEnrollDateEq(memberSearch.getMemberEnrollDate())
            )
            .groupBy(member.id)
            .having(
                contractNumberLoe(memberSearch.getContractCount()),
                contractPriceLoeInGroup(memberSearch.getContractPrice())
            )
            .orderBy(buildOrderSpecifier(pageable)
                .orElse(member.enrollDate.desc()))
            .offset(pageable.getPage())
            .limit(pageable.getSize())
            .fetch();
    }

    /*
     * 전체 회원 수
     * */
    public int countAllMemberByVendor(Long vendorId, MemberSearchReq memberSearch) {
        JPQLQuery<Long> subquery = JPAExpressions
                .select(member.id)
                .from(member)
                .leftJoin(member.contracts, contract).on(contract.deleted.isFalse())
                .leftJoin(contract.contractProducts, contractProduct).on(contractProduct.deleted.isFalse())
                .where(
                        member.vendor.id.eq(vendorId),
                        memberNotDel(),

                        memberNameContains(memberSearch.getMemberName()),
                        memberPhoneContains(memberSearch.getMemberPhone()),
                        memberEmailContains(memberSearch.getMemberEmail()),
                        memberEnrollDateEq(memberSearch.getMemberEnrollDate())
                )
                .groupBy(member.id)
                .having(
                        contractNumberLoe(memberSearch.getContractCount()),
                        contractPriceLoeInGroup(memberSearch.getContractPrice())
                );

        Long res = jpaQueryFactory.select(member.id.count())
                .from(member)
                .where(member.id.in(subquery))
                .fetchOne();

        return (res != null) ? res.intValue() : 0;
    }

    /*
    * 회원 상세 조회 : 기본정보
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

    private BooleanExpression memberEmailContains(String memberEmail) {
        return StringUtils.hasText(memberEmail) ? member.email.contains(memberEmail) : null;
    }

    private BooleanExpression memberEnrollDateEq(LocalDate memberEnrollDate) {
        return (memberEnrollDate != null) ? member.enrollDate.eq(memberEnrollDate) : null;
    }

    private BooleanExpression contractNumberLoe(Integer contractNumber) {
        if (contractNumber == null) {
            return null;
        }
        return contract.countDistinct().loe(contractNumber);
    }
    private BooleanExpression contractPriceLoe(Long contractPrice) {
        if (contractPrice == null) {
            return null;
        }
        return contractProduct.price.longValue().multiply(contractProduct.quantity).loe(contractPrice);
    }
}