package org.zly.utils;


import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.zly.utils.network.ZlyHttpUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 加密工具类
 */
public class ZlyEncryptionUtils {
    public static void main(String[] args) {
        String sign;
        sign = getMd5Str("86106822","#","663a73f37c50f1e85ab41ccfb7b7cd46","#","f622b6c25196cdd0373a5be5d6494fe2");
        System.out.println(sign);
    }
    public static byte[] getMd5Byte(Object... str) {
        return getAlgorithmStr(false, "MD5", StandardCharsets.UTF_8, str);
//        MessageDigest md5 = null;
//        try {
//            md5 = MessageDigest.getInstance("MD5");
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//        char[] charArray = StringUtils.join(str).toCharArray();
//        byte[] byteArray = new byte[charArray.length];
//
//        for (int i = 0; i < charArray.length; i++) {
//            byteArray[i] = (byte) charArray[i];
//        }
//        return md5.digest(byteArray);
    }

    public static String getMd5Str(Object... str) {
        return byteChangeHexStr(getMd5Byte(str));
    }

    public static String getSHA256Str(Object... str) {
        return byteChangeHexStr(getSHA256Byte(str));
    }

    public static byte[] getSHA256Byte(Object... str) {
        return getAlgorithmStr(false, "SHA-256", StandardCharsets.UTF_8, str);
    }


    /**
     * 获取指定算法的字符串
     *
     * @param str
     * @param urlEndecode 是否需要url转码
     * @return
     */
    @SneakyThrows(NoSuchAlgorithmException.class)
    public static byte[] getAlgorithmStr(boolean urlEndecode, String algorithm, Charset encoder, Object... str) {
        if (str == null) throw new NullPointerException();
        if (str.length==0) throw new IllegalArgumentException("arrays  is empty");
        String string = StringUtils.join(str);
        if (urlEndecode) string = ZlyHttpUtils.getUrlEncoder(string, encoder);
        MessageDigest md5 = MessageDigest.getInstance(algorithm);
        md5.update(string.getBytes(StandardCharsets.UTF_8));
        return md5.digest();
    }

    /**
     * 将字节转成16进制字符
     *
     * @param bytes
     * @return
     */
    public static String byteChangeHexStr(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String t = Integer.toHexString(0xFF & b);
            if (t.length() == 1) {
                hexString.append("0").append(t);
            } else {
                hexString.append(t);
            }
        }
        return hexString.toString();
    }
}
