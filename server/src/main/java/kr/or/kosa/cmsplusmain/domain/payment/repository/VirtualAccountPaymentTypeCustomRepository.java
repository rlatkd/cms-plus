package kr.or.kosa.cmsplusmain.domain.payment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseCustomRepository;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.VirtualAccountPaymentType;

public class VirtualAccountPaymentTypeCustomRepository extends BaseCustomRepository<VirtualAccountPaymentType> {
    public VirtualAccountPaymentTypeCustomRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
        super(em, jpaQueryFactory);
    }
}
