package kr.or.kosa.cmsplusbatch.batch.reader;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.kosa.cmsplusbatch.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusbatch.domain.billing.entity.QBilling;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class ZeroOffsetItemReader implements ItemReader<Billing> {

    private final JPAQueryFactory queryFactory;
    private final LocalDate billingDate;
    private final int chunkSize;
    private long lastId = 0;
    private Iterator<Billing> billingIterator;

    @Override
    public Billing read() throws Exception {
        if (billingIterator == null || !billingIterator.hasNext()) {
            QBilling qBilling = QBilling.billing;

            List<Billing> billings = queryFactory.selectFrom(qBilling)
                    .where(qBilling.billingDate.eq(billingDate)
                            .and(qBilling.contract.member.autoInvoiceSend.isTrue())
                            .and(qBilling.deleted.isFalse())
                            .and(qBilling.id.gt(lastId))) // PK 조건 추가
                    .orderBy(qBilling.id.asc())
                    .limit(chunkSize)
                    .fetch();

            if (billings.isEmpty()) {
                return null; // No more items to read
            }

            billingIterator = billings.iterator();
            lastId = billings.get(billings.size() - 1).getId(); // 마지막 ID 저장
        }

        return billingIterator.hasNext() ? billingIterator.next() : null;
    }
}