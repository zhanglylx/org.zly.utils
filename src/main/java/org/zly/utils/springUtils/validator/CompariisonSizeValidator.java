package org.zly.utils.springUtils.validator;

import java.util.Date;

/**
 * @author zhanglianyu
 * @date 2023-07-24 11:32
 */
public interface CompariisonSizeValidator {
    boolean validatorHandler(Comparable<Object> first, Comparable<Object> two);
}
