package org.zly.utils.springUtils.validator;

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
public class CompariisonSizeValidatorImpl implements ConstraintValidator<ZlyCompariisonSizeValidator, Object> {

    private ZlyCompariisonSizeValidator zlyMultipleDateTime;

    public CompariisonSizeValidatorImpl() {
        System.out.println("-=====================================");
    }

    @Override
    public void initialize(ZlyCompariisonSizeValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.zlyMultipleDateTime = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        System.out.println("0000000000000000000000000000");
        if (StringUtils.isBlank(zlyMultipleDateTime.firstField()))
            throw new IllegalArgumentException("firstField 不能为空");
        if (StringUtils.isBlank(zlyMultipleDateTime.twoField())) throw new IllegalArgumentException("twoField 不能为空");
        final Comparable<Object> first = getComparable(value, zlyMultipleDateTime.firstField());
        final Comparable<Object> two = getComparable(value, zlyMultipleDateTime.twoField());
        final Class<? extends CompariisonSizeValidator> aClass = this.zlyMultipleDateTime.compariisonSizeValidator();
        if (aClass == null) throw new IllegalArgumentException("dateTimeValidatorClass 不能为空");
        try {
            final CompariisonSizeValidator compariisonSizeValidator = aClass.newInstance();
            return compariisonSizeValidator.validatorHandler(first, two);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("实例化dateTimeValidator失败", e);
            throw new RuntimeException("实例化dateTimeValidator失败");
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
