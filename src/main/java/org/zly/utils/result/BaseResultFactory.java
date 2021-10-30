package org.zly.utils.result;

public interface BaseResultFactory<T> {

    BaseResult<T> createErrorException(String msg, Exception exception);

    BaseResult<T> createErrorException(Exception exception);

    BaseResult<T> createErrorCommon(String msg);

    BaseResult<T> createSuccess(T data);

    BaseResult<T> createSuccess(String msg, T data);

    BaseResult<T> createErrorParameter(String... params);

}