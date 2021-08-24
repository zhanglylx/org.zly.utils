package org.zly.utils.network;

import org.apache.http.entity.ContentType;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public enum ZlyContentTypeEnum {
    //谷歌的protobuf协议type
    PROTOBUF("application/x-protobuf"),
    APPLICATION_JSON(ContentType.APPLICATION_JSON.getMimeType()),
    APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png");
//    Multipart("multipart/form-data", ZLYRandomUtils.getRandomString());


    public String getContentType() {
        return contentType;
    }

    private String contentType;


    private String boundary;

    private Charset charset;

    private ZlyContentTypeEnum(String contentType) {
        this.contentType = contentType;
        this.charset = StandardCharsets.UTF_8;
    }

    private ZlyContentTypeEnum(String contentType, String boundary) {
        this(contentType);
        this.boundary = boundary;
        this.contentType += "; boundary=" + this.boundary;
    }

    public Charset getCharset() {
        return this.charset;
    }


    public String toString() {
        return toString(getCharset());
    }

    public String toString(Charset charset) {
        return this.contentType + "; charset=" + charset.name();
    }

    public String getBoundary() {
        return boundary;
    }

    public static String getContentTypeName() {
        return "Content-Type";
    }

}
