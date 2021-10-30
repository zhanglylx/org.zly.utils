package org.zly.utils.result;

import org.apache.commons.lang3.StringUtils;

public class BaseResultFactoryImpl<T> implements BaseResultFactory<T> {

    @Override
    public BaseResult<T> createErrorException(String msg, Exception exception) {
        BaseResultBuilder<T> baseResultBuilder = new BaseResultBuilder<>();
        baseResultBuilder.setStatus(BaseResultStatusEnum.ERROR_SERVICE_EXCEPTIONS.getStatus());
        baseResultBuilder.setMsg(msg + " {" + exception.getMessage() + "}");
        return baseResultBuilder.build();
    }

    public BaseResult<T> createErrorException(Exception exception) {
        return createErrorException("服务发生未知异常", exception);
    }


    @Override
    public BaseResult<T> createErrorCommon(String msg) {
        BaseResultBuilder<T> baseResultBuilder = new BaseResultBuilder<>();
        baseResultBuilder.setStatus(BaseResultStatusEnum.ERROR_COMMON.getStatus());
        baseResultBuilder.setMsg(msg);
        return baseResultBuilder.build();
    }

    @Override
    public BaseResult<T> createSuccess(T data) {
        return createSuccess("", data);
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
    public BaseResult<T> createErrorParameter( String... params) {
        BaseResultBuilder<T> baseResultBuilder = new BaseResultBuilder<>();
        baseResultBuilder.setStatus(BaseResultStatusEnum.ERROR_PARAMETER.getStatus());
        baseResultBuilder.setMsg("参数错误" + " {" + StringUtils.join(params, ",") + "}");
        return baseResultBuilder.build();
    }
}
