package org.zly.utils.network;

import lombok.Data;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpRequestBase;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息头类型
 */
@Data
public class ZlyNetworkHeaders {
    private Map<String, Header> headers;
    private byte[] responseBytes;
    private HttpRequestBase httpRequestBase;
    private long responseTime;
    private int responseCode;
    private boolean isRedirection;
    //    跳转的URI，数组中最后一个是最终的访问接口
    private List<URI> redirectionUri;

    public ZlyNetworkHeaders() {
        this.headers = new HashMap<>();
        this.responseCode = -1;
        this.redirectionUri = new ArrayList<>();
    }

}
