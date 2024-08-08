package kr.or.kosa.cmsplusmain.domain.contract.dto.request;

import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractSearchReq {

	/****** 검색 가능 항목 *******/

	private Long contractId;
	private String memberName;
	private String memberPhone;
	private Integer contractDay;
	private Long contractPrice;
	private ContractStatus contractStatus;
	private String productName;
	private PaymentType paymentType;
	private PaymentMethod paymentMethod;

	private Long memberId;

	/****** 정렬 가능 항목 *******/
	// memberName
	// contractDay
	// contractPrice
}
