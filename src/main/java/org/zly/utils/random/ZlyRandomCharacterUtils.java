package org.zly.utils.random;

import org.apache.commons.lang3.ArrayUtils;
import org.zly.utils.random.character.CharRandomType;
import org.zly.utils.random.type.*;

import java.util.*;

/**
 * 随机工具
 */
public class ZlyRandomCharacterUtils {

    public static String nextString() {
        return ZlyRandomStrTypeCache.ONLY_STRING.nextRandom();
    }

    /**
     * @param number
     * @return 返回指定长度的字符串，包含数字，字母，特殊字符
     */
    public static String nextPassword(int number) {
        return ((ZlyRandomPassword) (ZlyRandomStrTypeCache.PASSWORD).getRandom()).nextRandom(number);
    }

    public static String nextUserId() {
        return ZlyRandomStrTypeCache.USER_ID.nextRandom();
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

//    public static Integer test(Double val) {
//        Integer type = null;
//        if (val < 10000) {
//            type = 1;
//        } else if (val < 20000) {
//            type = 2;
//        } else if (val < 100000) {
//            type = 3;
//        } else if (val >= 100000) {
//            type = 4;
//        }
//        return type;
//    }

//    public static String nextMixture(int i, int number, CharRandomType... charRandomTypes) {
//
//    }

    public static String nextMixtureExclude(CharRandomType... charRandomType) {
        return nextMixtureExclude(1, charRandomType);
    }

    public static String nextMixtureExclude(int min, int max, CharRandomType... charRandomType) {
        return nextMixtureExclude(ZlyRandomNumberUtils.nextInt(min, max), charRandomType);
    }

    public static String nextMixtureExclude(int number, CharRandomType... charRandomType) {
        if (charRandomType == null || charRandomType.length == 0) return nextMixture(charRandomType);
        return nextMixture(number, ArrayUtils.removeElements(CharRandomType.values(), charRandomType));
    }

    public static String nextMixture(int number, CharRandomType... charRandomType) {
        if (charRandomType == null || charRandomType.length == 0) charRandomType = CharRandomType.values();
        if (number < 1) throw new IllegalArgumentException("number不能小于1");
        StringBuilder stringBuilder = new StringBuilder();
        CharRandomType randomType;
        while (stringBuilder.length() != number) {
            randomType = ZlyRandomSetUtils.nextValue(charRandomType);
            long roomSize = number - stringBuilder.length();
            if (randomType.size() > roomSize) {
                charRandomType = ArrayUtils.removeElement(charRandomType, randomType);
                if (charRandomType.length == 0) throw new IllegalArgumentException("剩余空间不支持填充大小");
                continue;
            }
            stringBuilder.append(randomType.nextRandom(number, roomSize));
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
        return new ZlyRandomUUID().nextRandom();
    }

    public static String nextIpAddress() {
        return ZlyRandomStrTypeCache.IP_ADDRESS.nextRandom();
    }


    public static String nextRandomPhone() {
        return nextRandomPhone(1).get(0);
    }


    public static List<String> nextRandomPhone(int number) {
        return ZlyRandomStrTypeCache.MOBILE_PHONE.nextRandoms(number);
    }


}










