package org.zly.utils.spring.validator.comparable.twain;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author zhanglianyu
 * @date 2023-07-24 11:15
 */
@Target({TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {TwainComparableValidatorImpl.class})
@Repeatable(ZlyTwainComparableValidator.List.class)
public @interface ZlyTwainComparableValidator {

    /**
     * 起始
     *
     * @return 起始字段名称
     */
    String firstField();

    /**
     * 第二个字段
     *
     * @return 第二个字段名称
     */
    String twoField();

    String message() default " Twain Comparable verification fails";

    Class<? extends TwainComparableValidator> twainComparableValidator();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ZlyTwainComparableValidator[] value();
    }
}
