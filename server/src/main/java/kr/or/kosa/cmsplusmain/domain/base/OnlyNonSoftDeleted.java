package kr.or.kosa.cmsplusmain.domain.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.annotations.SQLRestriction;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SQLRestriction("deleted = false")
public @interface OnlyNonSoftDeleted {
}
