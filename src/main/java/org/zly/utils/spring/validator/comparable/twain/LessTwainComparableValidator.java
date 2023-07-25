package org.zly.utils.spring.validator.comparable.twain;

/**
 * 小于
 *
 * @author zhanglianyu
 * @date 2023-07-24 17:06
 */
public class LessTwainComparableValidator implements TwainComparableValidator {
    @Override
    public  boolean validatorHandler(Comparable<Object> first, Comparable<Object> two) {
        return first.compareTo(two) < 0;
    }
}
