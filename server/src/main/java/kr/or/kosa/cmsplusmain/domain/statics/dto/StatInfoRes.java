package kr.or.kosa.cmsplusmain.domain.statics.dto;

import kr.or.kosa.cmsplusmain.domain.statics.dto.query.BillingStatQueryRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.query.ContractStatQueryRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.query.MemberStatQueryRes;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatInfoRes {
	private final Long totalBillingPrice;
	private final Long totalPaidPrice;
	private final Long totalNotPaidPrice;

	private final Long totalContractCount;
	private final Long newContractCount;
	private final Long expectedToExpiredCount;

	private final Long totalMemberCount;
	private final Long newMemberCount;
	private final Long activeMemberCount;

	private Double memberGrowth;
	private Double billingPriceGrowth;
}
