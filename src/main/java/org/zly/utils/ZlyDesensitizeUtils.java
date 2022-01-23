package org.zly.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 敏感信息脱敏
 */
public class ZlyDesensitizeUtils {
    public static final String DEFAULT_DESENSITIZE = "*";

    public static String mobilePhone(String phone) {
        return numberHandler(phone, 3, 4, DEFAULT_DESENSITIZE, Type.MOBILE_PHONE);
    }


    public static String numberHandler(String phone, int from, int backNumber, String replace, ZlyDesensitizeUtils.Type type) {
        return desensitizeHandler(phone, from, backNumber, replace, type.getType());
    }

    public static String getNoDesensitizePre(String string) {
        return getNoDesensitizePre(string, true);
    }

    public static String getNoDesensitizePre(String string, boolean correlation) {
        return getNoDesensitizePre(string, DEFAULT_DESENSITIZE, correlation);
    }

    public static String getNoDesensitizePre(String string, String desensitizeStr, boolean correlation) {
        return noDesensitize(string, desensitizeStr, correlation, 1);
    }

    public static String getNoDesensitizeSuffix(String string, boolean correlation) {
        return getNoDesensitizeSuffix(string, DEFAULT_DESENSITIZE, correlation);
    }

    public static String getNoDesensitizeSuffix(String string) {
        return getNoDesensitizeSuffix(string, true);
    }

    public static String getNoDesensitizeSuffix(String string, String desensitizeStr, boolean correlation) {
        return noDesensitize(string, desensitizeStr, correlation, 2);
    }

    private static String noDesensitize(String str, String desensitizeStr, boolean correlation, int type) {
        if (StringUtils.isBlank(str)) throw new IllegalArgumentException("字符串不能为空");
        if (StringUtils.isBlank(desensitizeStr)) {
            if (correlation) throw new IllegalArgumentException("脱敏字符不能为空");
            return str;
        }
        if (!str.contains(desensitizeStr)) {
            if (correlation) throw new IllegalArgumentException("字符的未包含脱敏字符，字符串:" + str + "，脱敏字符:" + desensitizeStr);
            return str;
        } else {
            switch (type) {
                case 1:
                    return str.substring(0, str.indexOf(desensitizeStr));
                case 2:
                    return str.substring(str.lastIndexOf(desensitizeStr) + desensitizeStr.length());
                default:
                    throw new UnsupportedOperationException("不支持的类型:" + type);
            }
        }

    }

    /**
     * 脱敏
     *
     * @param str        字符
     * @param from       从第几位开始
     * @param backNumber 后面保留几位
     * @param replace    替换符
     * @return 脱敏后的手机号
     */
    public static String desensitizeHandler(String str, int from, int backNumber, String replace, String regexType) {
        Objects.requireNonNull(str);
        int number = str.length() - from - backNumber;
        final String s1 = number > 0 ? StringUtils.leftPad(replace, number, replace) : "";
//        "(\\d{" + from + "})\\d*(\\d{" + backNumber + "})"
        return str.replaceAll(
                "(" + regexType + "{" + from + "})" + regexType + "*(" + regexType + "{" + backNumber + "})"
                , "$1" + s1 + "$2");
    }

    public static void main(String[] args) {
        numberHandler("17710893436", 3, 1, "*", Type.MOBILE_PHONE);
    }

    public static enum Type {
        NUMBER("\\d"),
        MOBILE_PHONE(NUMBER.getType());


        Type(String type) {
            this.type = type;
        }

        private String type;

        public String getType() {
            return type;
        }
    }
}
