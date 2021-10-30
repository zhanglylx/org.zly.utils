package org.zly.utils.random;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 随机工具
 */
public class ZlyRandomCharacterUtils {

    private static final Object NEXT_STRING_UNIQUE_KEY_ARRAY_LOCK = new Object();
    private static AtomicLong[] NEXT_STRING_UNIQUE_KEY_ARRAY = new AtomicLong[]{nextUniqueKey()};

    private static AtomicLong nextUniqueKey() {
        return new AtomicLong(System.currentTimeMillis());
    }

    public static String nextString() {
        if (NEXT_STRING_UNIQUE_KEY_ARRAY[NEXT_STRING_UNIQUE_KEY_ARRAY.length - 1].get() == Long.MAX_VALUE) {
            synchronized (NEXT_STRING_UNIQUE_KEY_ARRAY_LOCK) {
                if (NEXT_STRING_UNIQUE_KEY_ARRAY[NEXT_STRING_UNIQUE_KEY_ARRAY.length - 1].get() == Long.MAX_VALUE) {
                    NEXT_STRING_UNIQUE_KEY_ARRAY = ArrayUtils.add(NEXT_STRING_UNIQUE_KEY_ARRAY, nextUniqueKey());
                }
            }
        }
        StringBuilder key = new StringBuilder();
        for (AtomicLong atomicLong : NEXT_STRING_UNIQUE_KEY_ARRAY) {
            key.append(atomicLong.incrementAndGet());
        }
        return nextUUID() + key;
    }

    /**
     * @param lenth
     * @return 返回指定长度的字符串，包含数字，字母，特殊字符
     */
    public static String nextPassword(int lenth) {
        StringBuilder stringBuilder = new StringBuilder();
        Object s;
        for (int i = 0; i < lenth; i++) {
            s = ZlyRandomSetUtils.nextValue(nextEnglish(), nextSpecialCharacter(), nextNumbers());
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    public static String nextPassword(int min, int max) {
        return nextPassword(ZlyRandNumberUtils.nextInt(min, max));
    }


    /**
     * 随机生成指定返回随机个数的中文
     *
     * @param min
     * @param max
     * @return
     */
    public static String nextChinese(int min, int max) {
        return nextChinese(ZlyRandNumberUtils.nextInt(min, max));
    }

    /**
     * 常见的中文字符
     *
     * @param number 数量
     * @return
     */
    public static String nextChinese(int number) {
        if (number < 0) throw new IllegalArgumentException("参数小于0");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < number; i++) {
            stringBuilder.append(nextChinese());
        }
        return stringBuilder.toString();
    }

    /**
     * 常见的中文字符
     *
     * @return
     */
    public static String nextChinese() {
        int hightPos, lowPos; // 定义高低位
        Random random = new Random();
        hightPos = (176 + Math.abs(random.nextInt(39)));// 获取高位值
        lowPos = (161 + Math.abs(random.nextInt(93)));// 获取低位值
        byte[] b = new byte[2];
        b[0] = (new Integer(hightPos).byteValue());
        b[1] = (new Integer(lowPos).byteValue());
        try {
            return new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String nextEnglish(int number) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < number; i++) {
            stringBuilder.append(nextEnglish());
        }
        return stringBuilder.toString();
    }

    public static String nextEnglish(int min, int max) {
        return nextEnglish(ZlyRandNumberUtils.nextInt(min, max));
    }

    /**
     * 混出英文和中文，长度根据指定的大小随机生成长度
     *
     * @param min 字符串最小长度
     * @param max 字符串最大长度
     * @return
     */
    public static String nextMixtureChinese_English(int min, int max) {
        return nextMixtureChinese_English(ZlyRandNumberUtils.nextInt(min, max));
    }

    /**
     * 混出英文、中文、数字
     *
     * @param min 字符串最小长度
     * @param max 字符串最大长度
     * @return 根据最小和最大长度随机生成长度的英文、中文、数字的字符串
     */
    public static String nextMixtureChinese_English_Number(int min, int max) {
        return nextMixtureChinese_English_Number(ZlyRandNumberUtils.nextInt(min, max));
    }

    public static String nextNumbers() {
        return nextNumbers(1, 0, 10);
    }

    public static String nextNumbers(int len) {
        if (len < 0) throw new IllegalArgumentException("len小于0");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            stringBuilder.append(nextNumbers());
        }
        return stringBuilder.toString();
    }

    /**
     * 返回指定范围的指定数量的数字
     *
     * @param len 长度
     * @param min 最小范围
     * @param max 最大范围：不包含最大
     * @return 随机生成的数字，如果max大于10，那么长度将可能大于指定的长度
     */
    public static String nextNumbers(int len, int min, int max) {
        if (len < 0) throw new IllegalArgumentException("len小于0");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            stringBuilder.append(ZlyRandNumberUtils.nextInt(min, max));
        }
        return stringBuilder.toString();
    }

    public static String nextNumbers(int len, int min) {
        return nextNumbers(len, min, 10);
    }


    public static String nextMixtureEnglish_Number(int min, int max) {
        return nextMixtureEnglish_Number(ZlyRandNumberUtils.nextInt(min, max));
    }

    public static String nextMixtureEnglish_Number(int len) {
        if (len < 0) throw new IllegalArgumentException("len小于0");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            stringBuilder.append(ZlyRandomSetUtils.nextValue(nextNumbers(), nextEnglish()));
        }
        return stringBuilder.toString();
    }

    /**
     * 混出英文、中文、数字
     *
     * @param number 字符串长度
     * @return
     */
    public static String nextMixtureChinese_English_Number(int number) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < number; i++) {
            stringBuilder.append(ZlyRandomSetUtils.nextValue(
                    nextEnglish()
                    , nextNumbers()
                    , nextChinese()
            ));
        }
        return stringBuilder.toString();
    }


    /**
     * 混出英文和中文
     *
     * @param number
     * @return
     */
    public static String nextMixtureChinese_English(int number) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < number; i++) {
            if (ZlyRandNumberUtils.nextInt(0, 2) == 0) {
                stringBuilder.append(nextChinese());
            } else {
                stringBuilder.append(nextEnglish());
            }
        }
        return stringBuilder.toString();
    }


    /**
     * 随机产生英文
     *
     * @return 返回一个英文字符串，大小写随机
     */
    public static String nextEnglish() {
        String str;
        str = (char) (Math.random() * 26 + 'A') + "";
        if (ZlyRandNumberUtils.nextInt(0, 2) == 0) str = str.toLowerCase();
        return str;
    }


    /**
     * 获取随机的特殊字符
     *
     * @return 只返回为英文的特殊字符
     */
    public static String nextSpecialCharacter() {
        char[] chars = ("`~!@#$%^&*()_-+=/*,.<>][{}\\|/?;:'\"").toCharArray();
        return String.valueOf(chars[ZlyRandomSetUtils.nextIndex(chars)]);
    }


    public static String nextUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String nextIpAddress() {
        return
                ZlyRandNumberUtils.nextInt(10, 1000)
                        + "." + ZlyRandNumberUtils.nextInt(1, 1000)
                        + "." + ZlyRandNumberUtils.nextInt(1, 1000)
                        + "." + ZlyRandNumberUtils.nextInt(1, 1000)
                ;
    }

    public static String nextRandomPhone() {
        return nextRandomPhone(1).get(0);
    }


    public static List<String> nextRandomPhone(int num) {
        Set<String> s = new HashSet<>(num);
//        long first = (long) Math.pow(10, 10);
//        long two = (long) Math.pow(10, 9);
        while (s.size() < num) {
//            s.add(first + (ZlySetUtils.nextValue(3, 5, 6, 7, 8, 9) * two) + nextLongLenth(9));
            s.add("1" + ZlyRandomSetUtils.nextValue(3, 5, 6, 7, 8, 9) + ZlyRandNumberUtils.nextLongLenth(9));
        }
        return new ArrayList<>(s);
    }

}
