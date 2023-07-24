package org.zly.utils.springUtils.validator;

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
@Constraint(validatedBy = {CompariisonSizeValidatorImpl.class})
@Repeatable(ZlyCompariisonSizeValidator.List.class)
public @interface ZlyCompariisonSizeValidator {

    /**
     * 起始日期
     *
     * @return 起始日期字段名称
     */
    String firstField();

    /**
     * 结束日期
     *
     * @return 结束日期字段名称
     */
    String twoField();

    String message() default "verification fails";

    Class<? extends CompariisonSizeValidator> compariisonSizeValidator();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ZlyCompariisonSizeValidator[] value();
    }
}
