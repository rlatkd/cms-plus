//package kr.or.kosa.cmsplusbatch.batch.reader;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import kr.or.kosa.cmsplusbatch.batch.dto.BillingQueryDto;
//import kr.or.kosa.cmsplusbatch.batch.dto.QBillingQueryDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.item.ItemReader;
//
//import java.time.LocalDate;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import static kr.or.kosa.cmsplusbatch.domain.billing.entity.QBilling.billing;
//import static kr.or.kosa.cmsplusbatch.domain.billing.entity.QBillingProduct.billingProduct;
//import static kr.or.kosa.cmsplusbatch.domain.contract.entity.QContract.contract;
//import static kr.or.kosa.cmsplusbatch.domain.member.entity.QMember.member;
//
//@RequiredArgsConstructor
//public class QuerydslItemReader implements ItemReader<BillingQueryDto> {
//
//    private final JPAQueryFactory queryFactory;
//    private final LocalDate billingDate;
//    private final int pageSize;
//    private long lastId = 0;
//    private Iterator<BillingQueryDto> billingIterator;
//
//    @Override
//    public BillingQueryDto read() throws Exception {
//        if (billingIterator == null || !billingIterator.hasNext()) {
//            // 기본 청구 정보를 조회합니다.
//            List<BillingQueryDto> billings = queryFactory
//                    .select(new QBillingQueryDto(
//                            billing.id,
//                            billing.contract.id,
//                            billing.billingType.stringValue(),
//                            billing.contractDay,
//                            billing.billingDate,
//                            billing.billingStatus.stringValue(),
//                            billing.invoiceMessage,
//                            billing.invoiceSendDateTime,
//                            billing.paidDateTime,
//                            member.name,
//                            member.phone
//                    ))
//                    .from(billing)
//                    .join(billing.contract, contract)
//                    .join(contract.member, member)
//                    .where(billing.billingDate.eq(billingDate)
//                            .and(member.autoInvoiceSend.isTrue())
//                            .and(billing.deleted.isFalse())
//                            .and(billing.id.gt(lastId))) // PK 조건 추가
//                    .orderBy(billing.id.asc())
//                    .limit(pageSize)
//                    .fetch();
//
//            if (billings.isEmpty()) {
//                return null; // 더 이상 읽을 항목이 없음
//            }
//
//            // 각각의 청구에 대해 청구 금액을 계산합니다.
//            List<Long> billingIds = billings.stream().map(BillingQueryDto::getId).collect(Collectors.toList());
//            Map<Long, Long> billingProductPrices = queryFactory
//                    .select(billingProduct.billing.id, billingProduct.price.multiply(billingProduct.quantity).sum().longValue())
//                    .from(billingProduct)
//                    .where(billingProduct.billing.id.in(billingIds))
//                    .groupBy(billingProduct.billing.id)
//                    .fetch()
//                    .stream()
//                    .collect(Collectors.toMap(
//                            tuple -> tuple.get(billingProduct.billing.id),
//                            tuple -> tuple.get(billingProduct.price.multiply(billingProduct.quantity).sum().longValue())
//                    ));
//
//            // 청구 정보에 청구 금액을 설정합니다.
//            for (BillingQueryDto billing : billings) {
//                billing.setTotalBillingProductPrice(billingProductPrices.getOrDefault(billing.getId(), 0L));
//            }
//
//            billingIterator = billings.iterator();
//            lastId = billings.get(billings.size() - 1).getId(); // 마지막 ID 저장
//        }
//
//        return billingIterator.hasNext() ? billingIterator.next() : null;
//    }
//
//    public void setLastId(long lastId) {
//        this.lastId = lastId;
//    }
//}
