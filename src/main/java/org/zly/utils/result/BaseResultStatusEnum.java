package org.zly.utils.result;

public enum BaseResultStatusEnum {
    SUCCEED(0),
    ERROR_COMMON(1),
    ERROR_PARAMETER(2),
    ERROR_SERVICE_EXCEPTIONS(3);


    private Integer status;

    BaseResultStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public BaseResult<?> setTestingCenterResultStatus(BaseResult<?> testCenterResult) {
        testCenterResult.setStatus(this.status);
        return testCenterResult;
    }
}
