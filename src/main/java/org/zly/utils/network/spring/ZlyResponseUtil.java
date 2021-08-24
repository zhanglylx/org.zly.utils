package org.zly.utils.network.spring;

import org.springframework.http.ResponseEntity;

import java.util.List;

public class ZlyResponseUtil {

    public static String getHeaderLocation(ResponseEntity<?> responseEntity) {
        if (!responseEntity.getStatusCode().is3xxRedirection())
            throw new RuntimeException("服务端返回状态不正确:" + responseEntity.getStatusCode());
        List<String> location = responseEntity.getHeaders().get("Location");
        if (location == null || location.size() == 0)
            throw new RuntimeException("获取Location异常:" + responseEntity.getHeaders());
        return location.get(0);
    }

}
