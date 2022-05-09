package org.zly.utils.network;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ZlyContentType {
    public static final String APPLICATION_JSON_VALUE = MediaType.APPLICATION_JSON_UTF8_VALUE ;
    public static final String CONTENT_TYPE = HttpHeaders.CONTENT_TYPE;
    public static final String CONTENT_DISPOSITION =HttpHeaders.CONTENT_DISPOSITION ;
    public static final String CONTENT_DISPOSITION_VALUE = "attchment;filename=%s";
    public static final String EXCEL_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static String contentDispositionValue(String filename) {
        return String.format(CONTENT_DISPOSITION_VALUE, ZlyHttpUtils.getUrlEncoder(filename));
    }

    public static void downloadMultipartHeader(HttpServletResponse httpServletResponse, String fileName) {
        final Map<String, String> stringStringMap = downloadMultipartHeader(fileName);
        httpServletResponse.setContentType(stringStringMap.get(CONTENT_TYPE));
        httpServletResponse.setHeader(CONTENT_DISPOSITION, stringStringMap.get(CONTENT_DISPOSITION));
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
