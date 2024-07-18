package kr.or.kosa.cmsplusmain.domain.payment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseCustomRepository;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import org.springframework.stereotype.Repository;

import static kr.or.kosa.cmsplusmain.domain.payment.entity.QPayment.payment;

@Repository
public class PaymentCustomRepository extends BaseCustomRepository<Payment> {

    public PaymentCustomRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
        super(em, jpaQueryFactory);
    }

    /*
     *  결제 존재 여부 판단
     */
    public boolean isExistPaymentId(Long paymentId) {
        Integer res = jpaQueryFactory
                .selectOne()
                .from(payment)
                .where(
                        payment.id.eq(paymentId),
                        payment.deleted.isFalse()
                )
                .fetchOne();
        return res != null;
    }
}
