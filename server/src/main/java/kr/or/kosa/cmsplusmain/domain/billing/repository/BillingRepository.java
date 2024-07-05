package kr.or.kosa.cmsplusmain.domain.billing.repository;

import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBilling.*;
import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingProduct.*;
import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingStandard.*;
import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.*;
import static kr.or.kosa.cmsplusmain.domain.payment.entity.QPayment.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseRepository;
import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItem;
import kr.or.kosa.cmsplusmain.domain.billing.dto.QBillingListItem;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;

@Repository
public class BillingRepository extends BaseRepository<Billing, Long> {

	public BillingRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
		super(em, jpaQueryFactory);
	}

	/*
	* 청구목록
	* */
	public List<BillingListItem> findBillingsByContractId(Long contractId) {
		return jpaQueryFactory
			.select(new QBillingListItem(
				billing.id,
				member.name,
				billing.billingDate,
				billing.billingProducts,
				billing.status,
				payment.paymentType,
				billingStandard.contractDate))
			.from(billing)
			.join(billing.billingStandard, billingStandard)
			.join(billing.billingStandard.member, member)
			.join(billing.billingStandard.contract.payment, payment)
			.join(billing.billingProducts, billingProduct)
			.where(
				billing.deleted.eq(false),
				billingProduct.deleted.eq(false),
				billingStandard.contract.id.eq(contractId))
			.fetch();
	}
}
