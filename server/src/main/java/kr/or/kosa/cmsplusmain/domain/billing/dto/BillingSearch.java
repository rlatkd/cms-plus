package kr.or.kosa.cmsplusmain.domain.billing.dto;

import java.time.LocalDate;

import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingSearch {

	/****** 검색 가능 항목 *******/

	private String memberName;
	private String memberPhone;
	private Long billingPrice;
	private String productName;
	private BillingStatus billingStatus;
	private PaymentType paymentType;
	private Integer contractDay;
}
