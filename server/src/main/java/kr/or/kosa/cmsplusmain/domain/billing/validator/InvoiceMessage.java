package kr.or.kosa.cmsplusmain.domain.billing.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = InvoiceMessageValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InvoiceMessage {
	String message() default "유효하지 않은 청구서 메시지";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
