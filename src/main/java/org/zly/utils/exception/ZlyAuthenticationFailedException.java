package org.zly.utils.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * 验证失败异常
 *
 * @author zhanglianyu
 * @date 2022-05-14 10:21
 */
public class ZlyAuthenticationFailedException extends RuntimeException {
    public ZlyAuthenticationFailedException() {
    }

    public ZlyAuthenticationFailedException(String... message) {
        super(StringUtils.join(message, ""));
    }

    public ZlyAuthenticationFailedException(String message) {
        super("验证失败【" + message + "】");
    }

    public ZlyAuthenticationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZlyAuthenticationFailedException(Throwable cause) {
        super(cause);
    }


    public ZlyAuthenticationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
