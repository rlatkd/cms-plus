package kr.or.kosa.cmsplusmain.domain.billing.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BillingCreateReq {
	@NotNull(message = "청구타입이 필요합니다")
	private BillingType billingType;					// 청구타입 (정기, 추가)

	@NotNull(message = "청구 결제일이 필요합니다")
	private LocalDate billingDate;						// 청구 결제일

	@NotNull(message = "계약 정보가 필요합니다")
	private Long contractId;							// 청구 생성 기반 계약의 ID

	@NotNull(message = "청구상품이 필요합니다")
	@Size(
		min = Billing.MIN_BILLING_PRODUCT_NUMBER,
		message = Billing.MIN_BILLING_PRODUCT_NUMBER + "개 이상의 상품이 필요합니다")
	private List<@Valid BillingProductReq> products;	// 청구 상품 목록
}
