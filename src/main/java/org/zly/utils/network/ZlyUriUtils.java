package org.zly.utils.network;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ZlyUriUtils {
    public static URI getUri(URIBuilder uriBuilder) {
        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static URI getUri(String url) {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static URI getUri(String url, String param) {
        URI uri;
        try {
            if (param == null || param.equals("")) {
                uri = new URIBuilder(url.trim()).build();
            } else {
                uri = new URIBuilder(url.trim()).setCustomQuery(param.trim()).build();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return uri;
    }

    public static URIBuilder getUriBuilder(URIBuilder uriBuilder) {
        try {
            return new URIBuilder(uriBuilder.build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static URIBuilder getUriBuilder(String url) {
        try {
            return new URIBuilder(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取请求地址中的某个参数
     *
     * @param url
     * @param name
     * @return
     */
    public static String getParam(String url, String name) {
        return getUrlQueryMap(url).get(name);
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param url url地址
     * @return url请求参数部分
     */
    private static String getUrlQuery(String url) {
        if (StringUtils.isBlank(url)) return null;
        final int urlIndex = url.indexOf("?");
        if (urlIndex == -1) return url;
        return url.substring(urlIndex + 1);
    }

    /**
     * 将参数存入map集合
     *
     * @param url url地址
     * @return url请求参数部分存入map集合
     */
    public static Map<String, String> getUrlQueryMap(String url) {
        Map<String, String> mapRequest = new HashMap<>();
        String[] arrSplit = null;
        String strUrlParam = getUrlQuery(url);
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (StringUtils.isNotBlank(arrSplitEqual[0])) {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], null);
                }
            }
        }
        return mapRequest;
    }
}
