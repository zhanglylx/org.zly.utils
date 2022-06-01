package org.zly.utils.exception;

/**
 * @author zhanglianyu
 * @date 2022-05-14 10:21
 */
public class NotFindException extends RuntimeException {
    public NotFindException() {
    }


    public NotFindException(String message) {
        super("未找到【" + message + "】");
    }

    public NotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFindException(Throwable cause) {
        super(cause);
    }

    public NotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
