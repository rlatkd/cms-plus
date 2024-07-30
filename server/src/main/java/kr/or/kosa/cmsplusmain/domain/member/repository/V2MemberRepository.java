package kr.or.kosa.cmsplusmain.domain.member.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.repository.V2BaseRepository;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberListItemRes;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberSearchReq;
import kr.or.kosa.cmsplusmain.domain.member.dto.QMemberListItemRes;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.contract;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContractProduct.contractProduct;
import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.member;
import static kr.or.kosa.cmsplusmain.util.QueryExpressions.*;

@Repository
public class V2MemberRepository extends V2BaseRepository<Member, Long> {

    private final JPAQueryFactory jpaQueryFactory;

    public V2MemberRepository(JPAQueryFactory jpaQueryFactory) {
        super();
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<MemberListItemRes> searchAllMemberByVendor(Long vendorId, MemberSearchReq memberSearch, PageReq pageable) {


        // 계약 금액 합계와 계약 건수를 계산하는 서브쿼리
        NumberExpression<Integer> contractPriceSum = new CaseBuilder()
                .when(contract.isNotNull().and(contract.member.eq(member)))
                .then(contractProduct.price.multiply(contractProduct.quantity))
                .otherwise(Integer.valueOf(0))
                .sum();

        NumberExpression<Integer> contractCount = new CaseBuilder()
                .when(contract.isNotNull().and(contract.member.eq(member)))
                .then(Integer.valueOf(1))
                .otherwise(Integer.valueOf(0))
                .sum();

        JPAQuery<MemberListItemRes> query = selectWithNotDel(new QMemberListItemRes(
                        member.id,
                        member.name,
                        member.phone,
                        member.email,
                        member.enrollDate,
                        contractCount.longValue(),
                        contractPriceSum
                ), member)
                .where(
                        member.vendor.id.eq(vendorId),
                        memberNameContains(memberSearch.getMemberName()),
                        memberPhoneContains(memberSearch.getMemberPhone()),
                        memberEmailContains(memberSearch.getMemberEmail()),
                        memberEnrollDateEq(memberSearch.getMemberEnrollDate())
                )
                .groupBy(member.id);


        // 계약 금액 합계와 계약 건수에 대한 having 조건 추가
        if (memberSearch.getContractPrice() != null) {
            query.having(contractPriceSum.goe(memberSearch.getContractPrice()));
        }
        if (memberSearch.getContractCount() != null) {
            query.having(contractCount.loe(memberSearch.getContractCount()));
        }

        return query
                .orderBy(buildMemberOrderSpecifier(pageable))
                .offset(pageable.getPage())
                .limit(pageable.getSize())
                .fetch();
    }



    /**
     * 회원 목록 조회
     * 기본 정렬: 최신 등록된 회원(회원 등록일 기준, not db 생성)
     */
//    public List<MemberListItemRes> searchAllMemberByVendor2(Long vendorId, MemberSearchReq memberSearch, PageReq pageable) {
//
//        // 계약 금액 합계와 계약 건수를 계산하는 서브쿼리
//        SimpleExpression<Integer> contractPriceSum = new CaseBuilder()
//                .when(contract.isNotNull())
//                .then(
//                        JPAExpressions.select(contractProduct.price.multiply(contractProduct.quantity).sum())
//                                .from(contractProduct)
//                                .where(contractProduct.contract.eq(contract))
//                )
//                .otherwise(0);
//
//        NumberExpression<Long> contractCount = new CaseBuilder()
//                .when(contract.isNotNull())
//                .then(1L)
//                .otherwise(0L)
//                .sum();
//
//        List<Member> members = jpaQueryFactory
//                .selectFrom(member)
//                .where(
//                        member.vendor.id.eq(vendorId),
//                        memberNotDel(),
//                        memberNameContains(memberSearch.getMemberName()),
//                        memberPhoneContains(memberSearch.getMemberPhone()),
//                        memberEmailContains(memberSearch.getMemberEmail()),
//                        memberEnrollDateEq(memberSearch.getMemberEnrollDate())
//                )
//                .orderBy(buildMemberOrderSpecifier(pageable))
//                .offset(pageable.getPage())
//                .limit(pageable.getSize())
//                .fetch();
//
//        return members.stream()
//                .map(this::mapToMemberListItemRes)
//                .collect(Collectors.toList());
//    }

    private MemberListItemRes mapToMemberListItemRes(Member member) {
        Map<String, Object> contractStats = getContractStats(member.getId());
        Long contractPrice = (Long) contractStats.get("contractPrice");
        Integer contractCount = (Integer) contractStats.get("contractCount");

        return new MemberListItemRes(
                member.getId(),
                member.getName(),
                member.getPhone(),
                member.getEmail(),
                member.getEnrollDate(),
                contractPrice,
                contractCount
        );
    }

    private Map<String, Object> getContractStats(Long memberId) {
        Tuple result = jpaQueryFactory
                .select(
                        contractProduct.price.multiply(contractProduct.quantity).sum().as("contractPrice"),
                        contract.countDistinct().as("contractCount")
                )
                .from(contract)
                .leftJoin(contract.contractProducts, contractProduct).on(contractProduct.deleted.isFalse())
                .where(contract.member.id.eq(memberId)
                        .and(contract.deleted.isFalse()))
                .fetchOne();

        Map<String, Object> stats = new HashMap<>();
        stats.put("contractPrice", result.get(0, Long.class));
        stats.put("contractCount", result.get(1, Integer.class));
        return stats;
    }




    protected BooleanExpression contractPriceLoeInGroup(Long contractPrice) {
        if (contractPrice == null)
            return null;
        return contractProduct.price.multiply(contractProduct.quantity).sum().loe(contractPrice);
    }

    private BooleanExpression contractNumberLoe(Integer contractNumber) {
        if (contractNumber == null) {
            return null;
        }
        return contract.countDistinct().loe(contractNumber);
    }

    private OrderSpecifier<?> buildMemberOrderSpecifier(PageReq pageReq) {
        if (pageReq == null || !StringUtils.hasText(pageReq.getOrderBy())) {
            return member.createdDateTime.desc();
        }

        String orderBy = pageReq.getOrderBy();

        if (orderBy.equals("contractPrice")) {
            NumberExpression<Long> expression = contractProduct.price.longValue().multiply(contractProduct.quantity).sum();
            return pageReq.isAsc() ? expression.asc() : expression.desc();
        }

        if (orderBy.equals("contractCount")) {
            NumberExpression<Integer> expression = contract.countDistinct().intValue();
            return pageReq.isAsc() ? expression.asc() : expression.desc();
        }

        if (orderBy.equals("memberName")) {
            StringExpression expression = member.name;
            return pageReq.isAsc() ? expression.asc() : expression.desc();
        }

        return member.createdDateTime.desc();
    }
}
