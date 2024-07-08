package kr.or.kosa.cmsplusmain.domain.contract.entity;

import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContractSearch {

	/****** 검색 가능 항목 *******/

	private String memberName;
	private String memberPhone;
	private Integer contractDay;
	private Long contractPrice;
	private String productName;
	private ContractStatus contractStatus;
	private ConsentStatus consentStatus;
}
