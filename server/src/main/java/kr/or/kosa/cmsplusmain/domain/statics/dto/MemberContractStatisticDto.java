package kr.or.kosa.cmsplusmain.domain.statics.dto;

import com.querydsl.core.annotations.QueryProjection;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberContractStatisticDto {
    private Long memberId;
    private String memberName;
    private Integer enrollYear;
    private Integer contractDuration;
    private Long totalContractAmount;
    private String paymentMethod;
    private Boolean renewed;

    @QueryProjection
    public MemberContractStatisticDto(Long memberId, String memberName, LocalDate enrollDate, Integer contractDuration, Long totalContractAmount, PaymentMethod paymentMethod, Boolean renewed) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.enrollYear = enrollDate.getYear();
        this.contractDuration = contractDuration;
        this.totalContractAmount = totalContractAmount;
        this.paymentMethod = paymentMethod != null ? paymentMethod.getCode() : null;
        this.renewed = renewed;
    }
}