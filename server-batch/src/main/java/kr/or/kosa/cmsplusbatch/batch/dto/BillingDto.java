package kr.or.kosa.cmsplusbatch.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class BillingDto {
    private Long id;
    private String invoiceName;
    private Long billingPrice;
    private LocalDate billingDate;
    private String memberName;
    private String memberPhone;
}
