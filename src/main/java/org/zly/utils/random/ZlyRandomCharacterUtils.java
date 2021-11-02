package org.zly.utils.random;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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
     * @param number
     * @return 返回指定长度的字符串，包含数字，字母，特殊字符
     */
    public static String nextPassword(int number) {
        return nextMixture(number, CharType.NUMBER, CharType.ENGLISH, CharType.SPECIAL);
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
     * @return
     */
    public static String nextChinese() {
        return nextChinese(1);
    }

    /**
     * 常见的中文字符
     *
     * @param number 数量
     * @return
     */
    public static String nextChinese(int number) {
        return nextMixture(number, CharType.CHINESE);
    }

    /**
     * 随机产生英文
     *
     * @return 返回一个英文字符串，大小写随机
     */
    public static String nextEnglish() {
        return nextEnglish(1);
    }

    public static String nextEnglish(int min, int max) {
        return nextEnglish(ZlyRandNumberUtils.nextInt(min, max));
    }

    public static String nextEnglish(int number) {
        return nextMixture(number, CharType.ENGLISH);
    }


    public static String nextMixture(CharType... charType) {
        return nextMixture(1, charType);
    }

    public static String nextMixture(int number, CharType... charType) {
        if (charType == null || charType.length == 0) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < number; i++) {
            stringBuilder.append(ZlyRandomSetUtils.nextValue(charType).nextRandom()
            );
        }
        return stringBuilder.toString();
    }


    /**
     * 获取随机的特殊字符
     *
     * @return 只返回为英文的特殊字符
     */
    public static String nextSpecialCharacter() {
        return nextMixture(CharType.SPECIAL);
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


    public static List<String> nextRandomPhone(int number) {
        Set<String> s = new HashSet<>(number);
//        long first = (long) Math.pow(10, 10);
//        long two = (long) Math.pow(10, 9);
        while (s.size() < number) {
//            s.add(first + (ZlySetUtils.nextValue(3, 5, 6, 7, 8, 9) * two) + nextLongLenth(9));
            s.add("1" + ZlyRandomSetUtils.nextValue(3, 5, 6, 7, 8, 9) + ZlyRandNumberUtils.nextLongLenth(9));
        }
        return new ArrayList<>(s);
    }

}

class SpecialHandler implements RandomHandler<String> {

    private final static char[] CHARS = ("`~!@#$%^&*()_-+=/*,.<>][{}\\|/?;:'\"").toCharArray();

    @Override
    public String nextRandom() {
        return String.valueOf(CHARS[ZlyRandNumberUtils.nextInt(0, CHARS.length)]);
    }
}

class EnglishHandler implements RandomHandler<String> {

    @Override
    public String nextRandom() {
        String str;
        str = (char) (Math.random() * 26 + 'A') + "";
        if (ZlyRandNumberUtils.nextInt(0, 2) == 0) str = str.toLowerCase();
        return str;
    }
}

class ChineseHandler implements RandomHandler<String> {

    @Override
    public String nextRandom() {
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
}

class NumberHandler implements RandomHandler<String> {

    @Override
    public String nextRandom() {
        return String.valueOf(ZlyRandNumberUtils.nextInt(10));
    }
}

enum CharType {
    CHINESE(new ChineseHandler()),
    ENGLISH(new EnglishHandler()),
    NUMBER(new NumberHandler()),
    SPECIAL(new SpecialHandler());
    private final RandomHandler<String> randomHandler;

    CharType(RandomHandler<String> randomHandler) {
        this.randomHandler = randomHandler;
    }

    public RandomHandler<String> getRandomHandler() {
        return randomHandler;
    }

    public String nextRandom() {
        return this.randomHandler.nextRandom();
    }
}
