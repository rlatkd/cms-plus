package kr.or.kosa.cmsplusmain.domain.contract.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ContractNameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContractName {
	String message() default "유효하지 않은 계약명";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
