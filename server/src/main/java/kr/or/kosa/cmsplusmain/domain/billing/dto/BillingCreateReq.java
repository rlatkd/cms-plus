package kr.or.kosa.cmsplusmain.domain.billing.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BillingCreateReq {
	@NotNull private BillingType billingType;					// 청구타입 (정기, 추가)
	@NotNull private LocalDate billingDate;						// 청구 결제일
	@NotNull private Long contractId;							// 청구 생성 기반 계약의 ID
	@NotNull private List<@Valid BillingProductReq> products;	// 청구 상품 목록
}
