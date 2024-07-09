package kr.or.kosa.cmsplusmain.domain.billing.dto;

import java.time.LocalDate;

import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingSearch {

	/****** 검색 가능 항목 *******/

	private String memberName;
	private String memberPhone;
	private Long billingPrice;    // 청구금액 이하
	private String productName;    // 상품 목록 중 포함
	private BillingStatus billingStatus;
	private PaymentType paymentType;
	private LocalDate billingDate;

	/****** 정렬 가능 항목 *******/
	// memberName
	// billingPrice
	// billingDate
}
