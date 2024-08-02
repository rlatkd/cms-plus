package kr.or.kosa.cmsplusmain.domain.billing.validator;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BillingDateReqValidator implements ConstraintValidator<BillingDateReq, LocalDate> {

	@Override
	public boolean isValid(LocalDate billingDateReq, ConstraintValidatorContext context) {
		if (billingDateReq == null) {
			return true; // null 값은 다른 어노테이션으로 처리
		}
		return billingDateReq.isAfter(LocalDate.now());
	}
}