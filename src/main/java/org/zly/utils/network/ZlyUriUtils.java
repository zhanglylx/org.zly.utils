package org.zly.utils.network;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class ZlyUriUtils {
    public static URI getUri(URIBuilder uriBuilder) {
        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static URI getUri(String url){
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
}
