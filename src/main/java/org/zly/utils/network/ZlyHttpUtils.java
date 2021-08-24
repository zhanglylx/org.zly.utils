package org.zly.utils.network;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * http类型，用于发送网络请求
 */
public class ZlyHttpUtils {
    public static final String ERR_REPONSE_CODE_KEY = "errReponseCode";
    // HTTP内容类型。相当于form表单的形式，提交数据
    public static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";
    // 超时时间:ms
    public static final int SOCKET_TIME_OUT = 600 * 1000;
    //创建连接的最长时间:ms
    public static final int CONNECTION_TIME_OUT = 60 * 2000;
    // 从连接池中获取到连接的最长时间:ms
    public static final int CONNECTION_REQUEST_TIME_OUT = 60 * 2000;
    // 线程池最大连接数
    public static final int POOL_MAX_TOTAL = 3 * 1000;
    //默认的每个路由的最大连接数
    public static final int POOL_MAX_PERROUTE = 10;
    //检查永久链接的可用性:ms
    public static final int POOL_VALIDATE_AFTER_INACTIVITY = 2 * 1000;
    //关闭Socket等待时间，单位:s
    public static final int SOCKET_LINGER = 60;
    //建立httpClient配置
    public static final HttpClientBuilder httpBulder;
    //    是否走本地代理
    public static boolean LOCAL_AGENT = true;
    //    是否重定向
    public static boolean REDIRECTION = false;

    static {
        try {
            //User-Agent
            String USER_AGENT = "zly";
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create
                    ().register(
                    "http", PlainConnectionSocketFactory.getSocketFactory()).register(
                    "https", sslsf).build();
            // 初始化连接管理器
            // 连接管理器
            PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            // 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
            pool.setMaxTotal(POOL_MAX_TOTAL);
            //默认的每个路由的最大连接数
            pool.setDefaultMaxPerRoute(POOL_MAX_PERROUTE);
            //官方推荐使用这个来检查永久链接的可用性，而不推荐每次请求的时候才去检查
            pool.setValidateAfterInactivity(POOL_VALIDATE_AFTER_INACTIVITY);
            //设置默认连接配置
            pool.setDefaultSocketConfig(setSocketConfig());
            //请求重试处理
            // 如果已经重试了3次，就放弃
            // 如果服务器丢掉了连接，那么就重试
            // 不要重试SSL握手异常
            // 超时
            // 目标服务器不可达
            // ssl握手异常
            // 如果请求是幂等的，就再次尝试
            //请求重试处理
            HttpRequestRetryHandler httpRequestRetryHandler = (exception, executionCount, context) -> {
                if (executionCount >= 3) return false;// 如果已经重试了3次，就放弃
                if (exception instanceof NoHttpResponseException) return true; // 如果服务器丢掉了连接，那么就重试
                if (exception instanceof SSLHandshakeException) return false; // 不要重试SSL握手异常
                if (exception instanceof InterruptedIOException) return false; // 超时
                if (exception instanceof UnknownHostException) return false;// 目标服务器不可达
                if (exception instanceof SSLException) return false;// ssl握手异常
                // 如果请求是幂等的，就再次尝试
                return !(HttpClientContext.adapt(context).getRequest()
                        instanceof HttpEntityEnclosingRequest);
            };
            List<Header> headers = new ArrayList<>();
            headers.add(new BasicHeader("Accept-Encoding", "chunked"));
            headers.add(new BasicHeader("Charset", StandardCharsets.UTF_8.name()));
            httpBulder = HttpClients.custom()
                    .setConnectionManager(pool) // 设置请求配置
                    .setDefaultRequestConfig(requestConfig())
                    .setUserAgent(USER_AGENT)
                    .setDefaultHeaders(headers)
//                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                    .setRetryHandler(httpRequestRetryHandler);  // 设置重试次数
//            ScheduledExecutorService service = Executors
//                    .newScheduledThreadPool(1);
//            service.scheduleAtFixedRate(new Runnable() {
//                @Override
//                public void run() {
//                    pool.closeExpiredConnections();
//                    pool.closeIdleConnections(5000, TimeUnit.SECONDS);
//                }
//            }, 1, 5, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * socket配置（默认配置 和 某个host的配置）
     */
    private static SocketConfig setSocketConfig() {
        return SocketConfig.custom()
                .setTcpNoDelay(true)     //是否立即发送数据，设置为true会关闭Socket缓冲，默认为false
                .setSoReuseAddress(true) //是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
                .setSoTimeout(SOCKET_TIME_OUT)       //接收数据的等待超时时间，单位ms
                .setSoLinger(SOCKET_LINGER)         //关闭Socket时，要么发送完所有数据，要么等待XXs后，就关闭连接，此时socket.closeAllPresentThread()是阻塞的
                .setSoKeepAlive(true)    //开启监视TCP连接是否有效
                .build();
    }

    /**
     * 构建请求配置信息
     * 超时时间
     */
    private static RequestConfig requestConfig() {
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIME_OUT) // 创建连接的最长时间
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIME_OUT) // 从连接池中获取到连接的最长时间
                .setSocketTimeout(SOCKET_TIME_OUT)
                .setRedirectsEnabled(REDIRECTION); // 数据传输的最长时间
        if (LOCAL_AGENT) {
            builder.setProxy(new HttpHost("localhost", 8888));
        }
        return builder.build();
    }

    private static CloseableHttpClient getHttpClient() {
        return httpBulder.build();
    }

    public static String doGet(String url, String param, Map<String, Object> headers) {
        return doGet(ZlyUriUtils.getUri(url, param), headers, null);
    }

    public static String doGet(String url, String param) {
        return doGet(ZlyUriUtils.getUri(url, param));
    }

    public static String doGet(URI uri) {
        return doGet(uri, null, null);
    }


    public static String doGet(URI uri, Map<String, Object> headers
            , ZlyNetworkHeaders zlyNetworkHeaders) {
        String result = null;
        try {
            result = getResponse(getHttpGet(uri, headers), zlyNetworkHeaders);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //不可以关闭，不然连接池就会被关闭
//            httpClient.closeAllPresentThread();
        }
        return result;
    }


    public static String doPost(String url, Object param) {
        return doPost(url, param, null, null);
    }

    public static String doPost(String url, Map<String, String> param) {
        return doPost(url, param, null, null);
    }

    public static String doPostMultipart(Object url
            , Consumer<MultipartEntityBuilder> consumer
            , Map<String, Object> requestHead
            , ZlyNetworkHeaders zlyNetworkHeaders) {
        String resultString = null;
        try {
            HttpPost httpPost = getHttpPost(url.toString(), requestHead);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            consumer.accept(multipartEntityBuilder);
            httpPost.setEntity(multipartEntityBuilder.build());
            resultString = getResponse(httpPost, zlyNetworkHeaders);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultString;
    }

    @SuppressWarnings("unchecked")
    public static String doPost(Object url
            , Object param
            , Map<String, Object> requestHead
            , ZlyNetworkHeaders zlyNetworkHeaders) {
        String resultString = null;

        try {
            HttpPost httpPost = getHttpPost(url.toString(), requestHead);
            // 创建参数列表
            if (param != null) {
                if (param instanceof Map) {
                    Map<String, Object> mapParam = (Map<String, Object>) param;
                    List<NameValuePair> paramList = new ArrayList<>();
                    for (String key : mapParam.keySet()) {
                        paramList.add(new BasicNameValuePair(key, String.valueOf(mapParam.get(key))));
                    }
                    // 模拟表单
                    httpPost.setEntity(new UrlEncodedFormEntity(paramList, StandardCharsets.UTF_8));
                } else if (param instanceof String) {
                    StringEntity entity = new StringEntity(param.toString(), StandardCharsets.UTF_8);
                    httpPost.setEntity(entity);
                } else if (param instanceof byte[]) {
                    httpPost.setEntity(new ByteArrayEntity((byte[]) param));
                } else if (param instanceof URIBuilder) {
                    return ZlyHttpUtils.doPost(url, ((URIBuilder) param).build(), requestHead, zlyNetworkHeaders);
                } else if (param instanceof URI) {
                    return ZlyHttpUtils.doPost(url, ((URI) param).getQuery(), requestHead, zlyNetworkHeaders);

                } else if (param instanceof File) {
//                    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
//                    FileBody file = new FileBody((File) param);
//                    multipartEntityBuilder.addPart("file", file);
//                    httpPost.setEntity(multipartEntityBuilder.build());
                    InputStream inputStream = new FileInputStream((File) param);
                    byte[] bytes = new byte[inputStream.available()];
                    inputStream.read(bytes);
                    inputStream.close();
                    boolean b = false;
                    for (Header header : httpPost.getAllHeaders()) {
                        if ("Content-Disposition".equals(header.getName())) {
                            b = true;
                            break;
                        }
                    }
                    if (!b) {
                        httpPost.addHeader("Content-Disposition", "form-data; name=\"file\"; filename=\"" + ((File) param).getName() + "\"");
//                        httpPost.addHeader("Content-type", "multipart/form-data; boundary=----WebKitFormBoundaryq6XehrXmGNAAFHRw");
                    }
                    httpPost.setEntity(new ByteArrayEntity(bytes));
                } else {
                    throw new RuntimeException("不支持的类型");
                }
            }
            resultString = getResponse(httpPost, zlyNetworkHeaders);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultString;
    }


//    @SuppressWarnings("unchecked")
//    public static byte[] doPostByte(Object url
//            , byte[] param
//            , Map<String, String> requestHead
//            , NetworkHeaders networkHeaders) {
//        try {
//            HttpPost httpPost = getHttpPost(url.toString(), requestHead);
//            // 创建参数列表
//            if (param != null) {
//                httpPost.setEntity(new ByteArrayEntity(param));
//            }
//            CloseableHttpClient closeableHttpClient = getHttpClient();
//            long startTime = System.currentTimeMillis();
//            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
//            if (networkHeaders != null) {
//                networkHeaders.setResponseTime(System.currentTimeMillis() - startTime);
//                networkHeaders.setHttpRequestBase(httpPost);
//            }
//            if (closeableHttpResponse.getStatusLine().getStatusCode() != 200) {
//                responseNetworkHeaders(closeableHttpResponse, networkHeaders);
//                return getErrCodeFormat(closeableHttpResponse.getStatusLine().getStatusCode()).getBytes(StandardCharsets.UTF_8);
//            } else {
//                responseNetworkHeaders(closeableHttpResponse, networkHeaders);
//                return EntityUtils.toByteArray(closeableHttpResponse.getEntity());
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }


    private static Header[] getHeaders(Map<String, Object> requestHead) {
        List<Header> headers = new ArrayList<>();
        if (null != requestHead) {
            for (Map.Entry<String, Object> entry : requestHead.entrySet()) {
                headers.add(new BasicHeader(entry.getKey(), String.valueOf(entry.getValue())));
            }
        }

        return headers.toArray(new Header[]{});
    }

    private static HttpGet getHttpGet(URI uri, Map<String, Object> headers) {
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeaders(getHeaders(headers));
        return httpGet;
    }

    private static HttpPost getHttpPost(String url, Map<String, Object> requestHead) {
        // 创建Http Post请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeaders(getHeaders(requestHead));
        return httpPost;
    }


    public static byte[] doGetByte(URI uri, Map<String, Object> headers
            , ZlyNetworkHeaders zlyNetworkHeaders) {
        byte[] result = null;
        try {
            result = (byte[]) getResponse(getHttpGet(uri, headers), zlyNetworkHeaders, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //不可以关闭，不然连接池就会被关闭
//            httpClient.closeAllPresentThread();
        }
        return result;
    }

    private static String getResponse(HttpRequestBase httpRequestBase
            , ZlyNetworkHeaders zlyNetworkHeaders) {
        return getURLDecoder((String) getResponse(httpRequestBase, zlyNetworkHeaders, true), StandardCharsets.UTF_8);
    }


    /**
     * 获取响应正文
     *
     * @param httpRequestBase
     * @param zlyNetworkHeaders
     * @return
     */
    private static Object getResponse(HttpRequestBase httpRequestBase
            , ZlyNetworkHeaders zlyNetworkHeaders, boolean string) {
        Object result;
        long startTime = System.currentTimeMillis();
        try (CloseableHttpResponse closeableHttpResponse = getHttpClient().execute(httpRequestBase)) {
            if (zlyNetworkHeaders != null) {
                zlyNetworkHeaders.setResponseTime(System.currentTimeMillis() - startTime);
                zlyNetworkHeaders.setHttpRequestBase(httpRequestBase);
            }
            if (closeableHttpResponse.getStatusLine().getStatusCode() >= 400) {
                result = getErrCodeFormat(closeableHttpResponse.getStatusLine().getStatusCode());
            } else if (closeableHttpResponse.getStatusLine().getStatusCode() >= 300) {
                URIBuilder ub = new URIBuilder(httpRequestBase.getURI());
                String location = closeableHttpResponse.getFirstHeader("Location").getValue();
                URIBuilder uriBuilder = new URIBuilder(location);
                if (StringUtils.isBlank(uriBuilder.getHost())) {
                    uriBuilder.setHost(ub.getHost());
                    uriBuilder.setPort(ub.getPort());
                }
                if (StringUtils.isBlank(uriBuilder.getFragment())) uriBuilder.setFragment(ub.getFragment());
                if (StringUtils.isBlank(uriBuilder.getScheme())) uriBuilder.setScheme(ub.getScheme());
//                将之前传入的参数传入到下一次请求中
//                List<NameValuePair> nameValuePairs = uriBuilder.getQueryParams();
//                nameValuePairs.removeAll(ub.getQueryParams());
//                uriBuilder.setParameters(nameValuePairs);
//                uriBuilder.addParameters(ub.getQueryParams());
                if (ub.toString().equals(uriBuilder.toString())) throw new RuntimeException("Redirect repetition");
//                如果请求方法不变，使用原httpRequestBase
                //                httpRequestBase.setURI(uriBuilder.build());
                URI uri = uriBuilder.build();
                HttpGet httpGet = new HttpGet(uri);
                httpGet.setHeaders(httpRequestBase.getAllHeaders());
                if (zlyNetworkHeaders != null) {
                    zlyNetworkHeaders.setRedirection(true);
                    zlyNetworkHeaders.getRedirectionUri().add(uri);
                }
                return getResponse(httpGet, zlyNetworkHeaders, string);
            } else {
                if (string) {
                    result = EntityUtils.toString(closeableHttpResponse.getEntity(), StandardCharsets.UTF_8);
                } else {
                    result = EntityUtils.toByteArray(closeableHttpResponse.getEntity());
                }

            }
            responseNetworkHeaders(closeableHttpResponse, zlyNetworkHeaders);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    /**
     * 将服务器响应头添加到networkHeaders
     *
     * @param closeableHttpResponse
     * @param zlyNetworkHeaders
     */
    private static void responseNetworkHeaders(CloseableHttpResponse closeableHttpResponse
            , ZlyNetworkHeaders zlyNetworkHeaders) {
        if (zlyNetworkHeaders != null) {
            zlyNetworkHeaders.setResponseCode(closeableHttpResponse.getStatusLine().getStatusCode());
            Map<String, Header> map = new HashMap<>();
            for (Header header : closeableHttpResponse.getAllHeaders()) {
                map.put(header.getName(), header);
            }
            zlyNetworkHeaders.setHeaders(map);
        }

    }

    /**
     * 设置本地代理
     */
    public static void LocalProxy() {
        System.setProperty("http.proxySet", "true"); //将请求通过本地发送
        System.setProperty("http.proxyHost", "127.0.0.1");  //将请求通过本地发送
        System.setProperty("http.proxyPort", "8888"); //将请求通过本地发送
    }

    public static String getUrlEncoder(String param) {
        return getUrlEncoder(param, StandardCharsets.UTF_8);
    }

    /**
     * 转码
     *
     * @param param
     * @return
     */
    public static String getUrlEncoder(String param, Charset encoder) {
        param = param.replace("\n", "\r\n");
        try {
            param = URLEncoder.encode(param, encoder.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return param;
    }


    /**
     * 默认UTF-8
     *
     * @param str
     * @return
     */
    public static String getURLDecoder(String str) {
        return getURLDecoder(str, StandardCharsets.UTF_8);
    }

    /**
     * URL 解码
     *
     * @return StringText
     * @author zhanglianyu
     * @date 2017.7.23
     */
    public static String getURLDecoder(String str, Charset encodingName) {
        String result = null;
        if (null == str) {
            throw new NullPointerException();
        }
        str = str.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        str = str.replaceAll("\\+", "%2B");
        if (str.indexOf("%", str.length() - 1) != -1) str = str.substring(0, str.length() - 1);
        try {
            result = URLDecoder.decode(str, encodingName.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    public static String toHttpGetParams(Map<String, Object> param) {
        return toHttpGetParams(param, false, StandardCharsets.UTF_8);
    }

    public static String toHttpGetParams(Map<String, Object> param, boolean isValueEncode) {
        return toHttpGetParams(param, isValueEncode, StandardCharsets.UTF_8);
    }

    /**
     * 这里只是其中的一种场景,也就是把参数用&符号进行连接且进行URL编码
     * 根据实际情况拼接参数
     */
    public static String toHttpGetParams(Map<String, Object> param, boolean isValueEncode, Charset valueEncoder) {
        StringBuilder res = new StringBuilder();
        if (param == null) {
            return null;
        }
        String key;
        Object value;
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            if ((key = entry.getKey()) == null) throw new NullPointerException("key exist null :" + param.toString());
            if ((value = entry.getValue()) == null) throw new NullPointerException("value is null : key=" + key);
            res.append(key);
            res.append("=");
            if (isValueEncode) {
                res.append(ZlyHttpUtils.getUrlEncoder(value.toString(), valueEncoder));
            } else {
                res.append(value.toString());
            }
            res.append("&");
        }
        return "".equals(res.toString()) ? "" : StringUtils.chop(res.toString());
    }


    //判断是否为16进制数
    private static boolean isHex(char c) {
        return ((c >= '0') && (c <= '9')) ||
                ((c >= 'a') && (c <= 'f')) ||
                ((c >= 'A') && (c <= 'F'));
    }

    private static String convertPercent(String str) {
        StringBuilder sb = new StringBuilder(str);

        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            //判断是否为转码符号%
            if (c == '%') {
                if (((i + 1) < sb.length() - 1) && ((i + 2) < sb.length() - 1)) {
                    char first = sb.charAt(i + 1);
                    char second = sb.charAt(i + 2);
                    //如只是普通的%则转为%25
                    if (!(isHex(first) && isHex(second)))
                        sb.insert(i + 1, "25");
                } else {//如只是普通的%则转为%25
                    sb.insert(i + 1, "25");
                }

            }
        }

        return sb.toString();
    }

    /**
     * 错误响应code格式化
     *
     * @return
     */
    public static String getErrCodeFormat(int errCode) {
        return "{\"" + ERR_REPONSE_CODE_KEY + "\":" + errCode + "}";
    }

    public static void addParameterURIBuilder(URIBuilder uriBuilder, String key, Object value) {
        Objects.requireNonNull(uriBuilder);
        Objects.requireNonNull(key);
        if (value != null) uriBuilder.addParameter(key, String.valueOf(value));
    }

    public static Map<String, Object> bodyMapPut(Object... keyValuePair) {
        return bodyMapPut(null, keyValuePair);
    }

    public static Map<String, Object> bodyMapPut(BiFunction<String, Object, Object> biFunction, Object... keyValuePair) {
        Map<String, Object> bodyMap = new HashMap<>();
        String key = null;
        for (int i = 0; i < keyValuePair.length; ++i) {
            if (i % 2 == 0) {
                if (keyValuePair[i] == null)
                    throw new IllegalArgumentException("key exist Blank:" + Arrays.toString(keyValuePair));
                key = keyValuePair[i].toString();
            } else {
                Object value = keyValuePair[i];
                if (biFunction != null) {
                    Object value1 = biFunction.apply(key, value);
                    if (value1 != null) value = value1;
                }
                if (value != null) {
                    if (StringUtils.isBlank(key))
                        throw new IllegalArgumentException("key is Blank:" + Arrays.toString(keyValuePair));
                    bodyMap.put(key, value);
                }
                key = null;
            }
        }
        return bodyMap;
    }


}
