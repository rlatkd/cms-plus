package kr.or.kosa.cmsplusmain.domain.base.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DayValidator implements ConstraintValidator<Day, Integer> {

	private static final int MAX_DAYS = 31;
	private static final int MIN_DAYS = 1;

	@Override
	public boolean isValid(Integer day, ConstraintValidatorContext context) {
		if (day == null) {
			return true; // null 값은 다른 어노테이션으로 처리
		}
		return day >= MIN_DAYS && day <= MAX_DAYS;
	}
}