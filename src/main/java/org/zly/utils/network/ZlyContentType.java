package org.zly.utils.network;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ZlyContentType {
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON_VALUE = "application/json;charset=utf-8";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String CONTENT_DISPOSITION_VALUE = "attchment;filename=%s";

    public static String contentDispositionValue(String filename) {
        return String.format(CONTENT_DISPOSITION_VALUE, filename);
    }

    public static void downloadMultipartHeader(HttpServletResponse httpServletResponse, String fileName) {
        httpServletResponse.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);
        httpServletResponse.setHeader(CONTENT_DISPOSITION, contentDispositionValue(fileName));
    }

    public static void downloadMultipartHeader(HttpHeaders header, String fileName) {
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        header.set(CONTENT_DISPOSITION, contentDispositionValue(fileName));
    }

    public static Map<String, String> downloadMultipartHeader(String fileName) {
        Map<String, String> map = new HashMap<>();
        map.put(CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);
        map.put(CONTENT_DISPOSITION, contentDispositionValue(fileName));
        return map;
    }

}
