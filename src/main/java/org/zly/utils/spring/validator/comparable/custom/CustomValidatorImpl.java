package org.zly.utils.spring.validator.comparable.custom;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

/**
 * 比较大小验证器，spirng默认实例化一次
 *
 * @author zhanglianyu
 * @date 2023-07-24 11:08
 */
@Slf4j
public class CustomValidatorImpl implements ConstraintValidator<ZlyCustomValidator, Object> {

    private ZlyCustomValidator zlyCustomValidator;

    @Override
    public void initialize(ZlyCustomValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.zlyCustomValidator = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null)
            throw new IllegalArgumentException("Custom Validator value is null:verification[" + context.getDefaultConstraintMessageTemplate() + "]fail");
        final Class<? extends CustomValidator> aClass = this.zlyCustomValidator.singleComparisonValidator();
        if (aClass == null) throw new IllegalArgumentException("singleComparisonValidator 不能为空");
        try {
            final CustomValidator customValidator = aClass.newInstance();
            return customValidator.validatorHandler(value, this.zlyCustomValidator);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("实例化singleComparisonValidator失败", e);
            throw new RuntimeException("实例化singleComparisonValidator失败");
        }
    }

}
