package org.zly.utils.springUtils.validator;

/**
 * 小于
 *
 * @author zhanglianyu
 * @date 2023-07-24 17:06
 */
public class LessCompariisonSizeValidator implements CompariisonSizeValidator {
    @Override
    public  boolean validatorHandler(Comparable<Object> first, Comparable<Object> two) {
        return first.compareTo(two) < 0;
    }
}
