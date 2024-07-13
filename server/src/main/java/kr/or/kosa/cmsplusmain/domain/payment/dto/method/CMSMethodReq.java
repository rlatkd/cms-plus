package kr.or.kosa.cmsplusmain.domain.payment.dto.method;

import java.time.LocalDate;

import kr.or.kosa.cmsplusmain.domain.payment.entity.Bank;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CMSMethodReq extends PaymentMethodInfoReq{
    private final Bank bank;
    private final String accountNumber;
    private final String accountOwner;
    private final LocalDate accountOwnerBirth;

    @Builder
    public CMSMethodReq(Bank bank, String accountNumber, String accountOwner, LocalDate accountOwnerBirth) {
        super(PaymentMethod.CMS);
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.accountOwner = accountOwner;
        this.accountOwnerBirth = accountOwnerBirth;
    }
}
