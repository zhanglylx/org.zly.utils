package org.zly.utils.spring.validator.comparable.custom;

import org.zly.utils.spring.validator.comparable.twain.TwainComparableValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author zhanglianyu
 * @date 2023-07-24 11:15
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {CustomValidatorImpl.class})
@Repeatable(ZlyCustomValidator.List.class)
public @interface ZlyCustomValidator {


    String message() default "Single  verification fails";

   Class<? extends CustomValidator> singleComparisonValidator();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({FIELD})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ZlyCustomValidator[] value();
    }
}