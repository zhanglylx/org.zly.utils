package org.zly.utils.spring.validator.comparable.twain;

/**
 * @author zhanglianyu
 * @date 2023-07-24 11:32
 */
public interface TwainComparableValidator {
    boolean validatorHandler(Comparable<Object> first, Comparable<Object> two);
}
