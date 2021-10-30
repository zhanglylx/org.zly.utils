package org.zly.utils.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResult<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

}
