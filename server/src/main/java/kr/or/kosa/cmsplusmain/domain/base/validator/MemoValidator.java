package kr.or.kosa.cmsplusmain.domain.base.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MemoValidator implements ConstraintValidator<Memo, String> {

	private static final int MAX_MEMO_LENGTH = 2000;

	@Override
	public void initialize(Memo constraintAnnotation) {
	}

	@Override
	public boolean isValid(String memo, ConstraintValidatorContext context) {
		if (memo == null) {
			return true; // null 값은 다른 어노테이션으로 처리
		}
		return memo.length() <= MAX_MEMO_LENGTH;
	}
}