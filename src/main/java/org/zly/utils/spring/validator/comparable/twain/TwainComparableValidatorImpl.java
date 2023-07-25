package org.zly.utils.spring.validator.comparable.twain;

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
public class TwainComparableValidatorImpl implements ConstraintValidator<ZlyTwainComparableValidator, Object> {

    private ZlyTwainComparableValidator zlyTwainComparableValidator;

    @Override
    public void initialize(ZlyTwainComparableValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.zlyTwainComparableValidator = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(zlyTwainComparableValidator.firstField()))
            throw new IllegalArgumentException("firstField 不能为空");
        if (StringUtils.isBlank(zlyTwainComparableValidator.twoField())) throw new IllegalArgumentException("twoField 不能为空");
        final Comparable<Object> first = getComparable(value, zlyTwainComparableValidator.firstField());
        final Comparable<Object> two = getComparable(value, zlyTwainComparableValidator.twoField());
        final Class<? extends TwainComparableValidator> aClass = this.zlyTwainComparableValidator.twainComparableValidator();
        if (aClass == null) throw new IllegalArgumentException("twainComparableValidator 不能为空");
        try {
            final TwainComparableValidator twainComparableValidator = aClass.newInstance();
            return twainComparableValidator.validatorHandler(first, two);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("实例化twainComparableValidator失败", e);
            throw new RuntimeException("实例化twainComparableValidator失败");
        }
    }

    @SuppressWarnings(value = "unchecked")
    private Comparable<Object> getComparable(Object value, String fieldName) {
        try {
            final Field field = value.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            final Object o = field.get(value);
            if (o instanceof Comparable) {
                return (Comparable<Object>) o;
            }
            throw new IllegalArgumentException(fieldName + " 没有实现Comparable");
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("没有获取到属性:" + fieldName);
        } catch (IllegalAccessException e) {
            log.error("获取属性内容失败:" + fieldName, e);
            throw new RuntimeException("获取属性内容失败:" + fieldName);
        }
    }
}
