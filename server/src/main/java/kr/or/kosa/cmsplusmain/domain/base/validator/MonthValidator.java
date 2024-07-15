package kr.or.kosa.cmsplusmain.domain.base.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MonthValidator implements ConstraintValidator<Month, Integer> {

	private static final int MAX_MONTH = 12;
	private static final int MIN_MONTH = 1;

	@Override
	public boolean isValid(Integer month, ConstraintValidatorContext context) {
		if (month == null) {
			return true; // null 값은 다른 어노테이션으로 처리
		}
		return month >= MIN_MONTH && month <= MAX_MONTH;
	}
}