package kr.or.kosa.cmsplusmain.domain.payment.validator;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CardNumberValidator implements ConstraintValidator<CardNumber, String> {

	private static final Pattern CARDNUMBER_PATTERN = Pattern.compile(
		"^\\d{16}$\n"
	);

	@Override
	public void initialize(CardNumber constraintAnnotation) {
	}

	// TODO
	@Override
	public boolean isValid(String cardNumber, ConstraintValidatorContext context) {
		return true;
		// if (cardNumber == null) {
		// 	return true; // null 값은 다른 어노테이션으로 처리
		// }
		// return CARDNUMBER_PATTERN.matcher(cardNumber).matches();
	}
}