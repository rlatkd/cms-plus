package kr.or.kosa.cmsplusmain.domain.billing.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = BillingDateReqValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BillingDateReq {
	String message() default "청구 결제일은 오늘 이후로만 가능합니다";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
