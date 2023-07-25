package org.zly.utils.spring.validator.comparable.custom;

/**
 * @author zhanglianyu
 * @date 2023-07-24 11:32
 */
public interface CustomValidator {
    boolean validatorHandler(Object value,ZlyCustomValidator zlyCustomValidator);
}
