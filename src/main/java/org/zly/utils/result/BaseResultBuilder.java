package org.zly.utils.result;

public class BaseResultBuilder<T> {
    private BaseResult<T> testResult;

    public BaseResultBuilder() {
        this.testResult = new BaseResult<>();
    }

    public int getStatus() {
        return this.testResult.getStatus();
    }

    public BaseResultBuilder<T> setStatus(int status) {
        this.testResult.setStatus(status);
        return this;
    }

    public BaseResultBuilder<T> setMsg(String msg) {
        this.testResult.setMsg(msg);
        return this;
    }

    public String getMsg() {
        return this.testResult.getMsg();
    }

    public T getData() {
        return this.testResult.getData();
    }

    public BaseResultBuilder<T> setData(T data) {
        this.testResult.setData(data);
        return this;
    }

    public BaseResult<T> build() {
        return this.testResult;
    }


}
