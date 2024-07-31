package kr.or.kosa.cmsplusmain.domain.statics.dto;

import com.querydsl.core.annotations.QueryProjection;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberContractStatisticDto {
    private Long memberId;
    private String memberName;
    private LocalDate enrollDate;
    private Integer contractDuration;
    private Long totalContractAmount;
    private String paymentMethod;
    private Boolean renewed;

    @QueryProjection
    public MemberContractStatisticDto(Long memberId, String memberName, LocalDate enrollDate, Integer contractDuration, Long totalContractAmount, PaymentMethod paymentMethod, Boolean renewed) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.enrollDate = enrollDate;
        this.contractDuration = contractDuration;
        this.totalContractAmount = totalContractAmount;
        this.paymentMethod = paymentMethod.getCode();
        this.renewed = renewed;
    }
}