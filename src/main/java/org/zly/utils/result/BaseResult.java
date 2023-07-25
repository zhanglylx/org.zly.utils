package org.zly.utils.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "BaseResult<Object> : 响应实体", description = "应用安装/更新bean")
public class BaseResult<T> implements Serializable {
    @ApiModelProperty("状态   SUCCEED(0),  ERROR_COMMON(1),   ERROR_PARAMETER(2),   ERROR_SERVICE_EXCEPTIONS(3);")
    private int status;
    @ApiModelProperty("消息")
    private String msg;
    @ApiModelProperty("响应实体内容")
    private T data;

}
