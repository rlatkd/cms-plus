package kr.or.kosa.cmsplusmain.domain.billing.dto;

import java.time.LocalDate;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;

import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingProduct;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentType;
import lombok.Getter;

@Getter
public class BillingListItem {

	private final Long billingId;
	private final String memberName;
	private final LocalDate billingDate;
	private final String firstProductName;
	private final Integer productCnt;
	private final Long billingPrice;
	private final String billingStatus;
	private final String paymentType;
	private final LocalDate contractDate;

	@QueryProjection
	public BillingListItem(Long billingId, String memberName, LocalDate billingDate, List<BillingProduct> billingProducts,
		BillingStatus billingStatus, String paymentType, LocalDate contractDate) {

		PaymentType mPaymentType = PaymentType.of(paymentType);

		this.billingId = billingId;
		this.memberName = memberName;
		this.billingDate = billingDate;
		this.firstProductName = billingProducts.isEmpty() ? null : billingProducts.get(0).getProduct().getName();
		this.productCnt = billingProducts.size();
		this.billingPrice = billingProducts.stream()
			.mapToLong(BillingProduct::getTotalPrice)
			.sum();
		this.billingStatus = (billingStatus != null) ? billingStatus.getTitle() : null; ;
		this.paymentType = (mPaymentType != null) ? mPaymentType.getTitle() : null;
		this.contractDate = contractDate;
	}
}
