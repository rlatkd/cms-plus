package kr.or.kosa.cmsplusmain.domain.contract.dto;

import java.time.LocalDateTime;
import java.util.List;

import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.payment.dto.PaymentDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractDetail {

	private final Long contractId;                                // 계약 ID
	private final String name;                                    // 계약이름
	private final LocalDateTime createdDateTime;                // 등록일시
	private final LocalDateTime modifiedDateTime;                // 변경일시
	private final List<ContractProductRes> contractProducts;    // 계약 상품 목록
	private final Long contractPrice;                            // 계약금액
	private final PaymentDto payment;                            // 결제정보

	public static ContractDetail fromEntity(Contract contract) {

		// NOT NULL
		throw new RuntimeException("Not implemented yet");
	}
}
