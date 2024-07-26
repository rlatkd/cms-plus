package kr.or.kosa.cmsplusmain.domain.billing.repository;

import static kr.or.kosa.cmsplusmain.domain.billing.entity.QBillingProduct.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.kosa.cmsplusmain.domain.base.repository.NewBaseRepository;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingProduct;

@Repository
public class NewBillingProductRepository extends NewBaseRepository<BillingProduct, Long> {

	public void deleteAllById(List<Long> ids) {
		update(billingProduct)
			.where(billingProduct.id.in(ids))
			.set(billingProduct.deleted, true)
			.execute();
	}
}
