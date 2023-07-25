package org.zly.utils.result;

import org.apache.commons.lang3.StringUtils;

public class BaseResultFactoryImpl<T> implements BaseResultFactory<T> {

    @Override
    public BaseResult<T> createErrorException(String msg, T object, Exception exception) {
        BaseResultBuilder<T> baseResultBuilder = new BaseResultBuilder<>();
        baseResultBuilder.setStatus(BaseResultStatusEnum.ERROR_SERVICE_EXCEPTIONS.getStatus());
        baseResultBuilder.setMsg(msg + " {" + exception.getMessage() + "}");
        baseResultBuilder.setData(object);
        return baseResultBuilder.build();
    }

    public BaseResult<T> createErrorException(Exception exception, T object) {
        return createErrorException("", object, exception);
    }

    @Override
    public BaseResult<T> createErrorException(String msg, Exception exception) {
        return createErrorException(msg, null, exception);
    }

    public BaseResult<T> createErrorException(Exception exception) {
        return createErrorException("服务发生未知异常", exception);
    }

    @Override
    public BaseResult<T> createErrorCommon(String msg) {
        return createErrorCommon(null,msg);
    }

    @Override
    public BaseResult<T> createErrorCommon(T data,String msg) {
        BaseResultBuilder<T> baseResultBuilder = new BaseResultBuilder<>();
        baseResultBuilder.setStatus(BaseResultStatusEnum.ERROR_COMMON.getStatus());
        baseResultBuilder.setMsg(msg);
        baseResultBuilder.setData(data);
        return baseResultBuilder.build();
    }

    @Override
    public BaseResult<T> createSuccess(T data) {
        return createSuccess("", data);
    }

    @Override
    public BaseResult<T> createSuccess() {
        return createSuccess(null);
    }

    @Override
    public BaseResult<T> createSuccess(String msg, T data) {
        BaseResultBuilder<T> baseResultBuilder = new BaseResultBuilder<>();
        baseResultBuilder.setStatus(BaseResultStatusEnum.SUCCEED.getStatus());
        baseResultBuilder.setMsg(msg);
        baseResultBuilder.setData(data);
        return baseResultBuilder.build();
    }

    @Override
    public BaseResult<T> createErrorParameter(String params) {
        return createErrorParameter(new String[]{params});
    }

    @Override
    public BaseResult<T> createErrorParameter(String[] params) {
        return createErrorParameter(null, params);
    }

    @Override
    public BaseResult<T> createErrorParameter(T data, String[] params) {
        BaseResultBuilder<T> baseResultBuilder = new BaseResultBuilder<>();
        baseResultBuilder.setStatus(BaseResultStatusEnum.ERROR_PARAMETER.getStatus());
        baseResultBuilder.setMsg("参数错误" + " {" + StringUtils.join(params, ",") + "}");
        baseResultBuilder.setData(data);
        return baseResultBuilder.build();
    }


    public static void main(String[] args) {
        System.out.println("842c3f4d:647dac7d:1:0".matches("^([0-9a-zA-Z]+:){3}[0-9a-zA-Z]$"));
    }
}
