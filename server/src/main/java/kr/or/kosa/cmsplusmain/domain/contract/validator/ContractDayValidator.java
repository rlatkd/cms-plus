package kr.or.kosa.cmsplusmain.domain.contract.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContractDayValidator implements ConstraintValidator<ContractDay, Integer> {

	private static final int MAX_DAYS = 31;
	private static final int MIN_DAYS = 1;

	@Override
	public boolean isValid(Integer contractDay, ConstraintValidatorContext context) {
		if (contractDay == null) {
			return true; // null 값은 다른 어노테이션으로 처리
		}
		return contractDay >= MIN_DAYS && contractDay <= MAX_DAYS;
	}
}