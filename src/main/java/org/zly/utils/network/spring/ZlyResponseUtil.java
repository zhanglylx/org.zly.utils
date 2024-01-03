package org.zly.utils.network.spring;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZlyResponseUtil {

    public static String getHeaderLocation(ResponseEntity<?> responseEntity) {
        if (!responseEntity.getStatusCode().is3xxRedirection())
            throw new RuntimeException("服务端返回状态不正确:" + responseEntity.getStatusCode());
        List<String> location = responseEntity.getHeaders().get("Location");
        if (location == null || location.size() == 0)
            throw new RuntimeException("获取Location异常:" + responseEntity.getHeaders());
        return location.get(0);
    }

    /**
     * 解析cookie
     * @param responseEntity
     * @return
     */
    public static Map<String, String> getHeaderCookies(ResponseEntity<?> responseEntity) {
        Map<String, String> map = new HashMap<>();
        final List<String> list = responseEntity.getHeaders().get(HttpHeaders.SET_COOKIE);
        if (list == null) return map;
        for (String heads : list) {
            if (!heads.contains(";")) {
                analysisHeaderCookies(map, heads);
            } else {
                final String[] split = heads.split(";");
                for (String s : split) {
                    analysisHeaderCookies(map, s);
                }
            }
        }
        return map;
    }

    private static void analysisHeaderCookies(Map<String, String> map, String head) {
        if (StringUtils.isBlank(head)) return;
        if (head.contains("=")) {
            final String[] split = head.split("=", 2);
            map.put(split[0], split[1]);
        } else {
            map.put(head, null);
        }
    }

}

