package org.zly.utils.random;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.zly.utils.random.character.CharRandomType;
import org.zly.utils.random.character.EmojiHandler;

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
        return key + nextUUID();
    }

    /**
     * @param number
     * @return 返回指定长度的字符串，包含数字，字母，特殊字符
     */
    public static String nextPassword(int number) {
        return nextMixture(number, CharRandomType.NUMBER, CharRandomType.ENGLISH, CharRandomType.SPECIAL);
    }

    public static String nextPassword(int min, int max) {
        return nextPassword(ZlyRandomNumberUtils.nextInt(min, max));
    }


    /**
     * 随机生成指定返回随机个数的中文
     *
     * @param min
     * @param max
     * @return
     */
    public static String nextChinese(int min, int max) {
        return nextChinese(ZlyRandomNumberUtils.nextInt(min, max));
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
        return nextMixture(number, CharRandomType.CHINESE);
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
        return nextEnglish(ZlyRandomNumberUtils.nextInt(min, max));
    }

    public static String nextEnglish(int number) {
        return nextMixture(number, CharRandomType.ENGLISH);
    }


    public static String nextMixture(CharRandomType... charRandomType) {
        return nextMixture(1, charRandomType);
    }

    public static String nextMixture(int min, int max, CharRandomType... charRandomType) {
        return nextMixture(ZlyRandomNumberUtils.nextInt(min, max), charRandomType);
    }

    public static void main(String[] args) {
        System.out.println(test(1D));
        System.out.println(test(9999.99));

        System.out.println(test(10000D));
        System.out.println(test(19999.99));

        System.out.println(test(20000.00));
        System.out.println(test(50000.00));
        System.out.println(test(99999.99));

        System.out.println(test(100000.00));
        System.out.println(test(1100000.00));

        System.out.println(DateUtils.isSameInstant(new Date(),new Date()));
        System.out.println(nextMixture(5000));
        System.out.println("\n".length());
    }


    public static Integer test(Double val) {
        Integer type = null;
        if (val < 10000) {
            type = 1;
        } else if (val < 20000) {
            type = 2;
        } else if (val < 100000) {
            type = 3;
        } else if (val >= 100000) {
            type = 4;
        }
        return type;
    }

    public static String nextMixture(int number, CharRandomType... charRandomType) {
        if (charRandomType == null || charRandomType.length == 0) charRandomType = CharRandomType.values();
        if (number < 0) throw new IllegalArgumentException("number不能小于0");
        StringBuilder stringBuilder = new StringBuilder();
        CharRandomType randomType;
        while (stringBuilder.length() != number) {
            randomType = ZlyRandomSetUtils.nextValue(charRandomType);
            while (true) {
                if (randomType.size() > number - stringBuilder.length()) {
                    if (charRandomType.length == 1) throw new IllegalArgumentException("剩余空间不支持填充大小");
                    charRandomType = ArrayUtils.removeElement(charRandomType, randomType);
                    randomType = ZlyRandomSetUtils.nextExclude(randomType, charRandomType);
                } else {
                    break;
                }
            }
            stringBuilder.append(randomType.nextRandom());
        }
        return stringBuilder.toString();
    }

    /**
     * 获取随机的特殊字符
     *
     * @return 只返回为英文的特殊字符
     */
    public static String nextSpecialCharacter() {
        return nextMixture(CharRandomType.SPECIAL);
    }


    public static String nextUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String nextIpAddress() {
        return
                ZlyRandomNumberUtils.nextInt(10, 1000)
                        + "." + ZlyRandomNumberUtils.nextInt(1, 1000)
                        + "." + ZlyRandomNumberUtils.nextInt(1, 1000)
                        + "." + ZlyRandomNumberUtils.nextInt(1, 1000)
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
            s.add("1" + ZlyRandomSetUtils.nextValue(new Integer[]{3, 5, 6, 7, 8, 9}) + ZlyRandomNumberUtils.nextLongLenth(9));
        }
        return new ArrayList<>(s);
    }

}










