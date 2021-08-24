package org.zly.utils.random;

/**
 * @author zly
 * @version 1.0
 * @date 2021/7/19 21:41
 */
public class ZlyRandomBooleanUtils {

    public static boolean getRandomBoolean() {
        return getRandomBoolean(null);
    }

    public static boolean getRandomBoolean(Boolean exclude) {
        if (exclude == null) return getRandomBoolean();
        return ZlyRandomSetUtils.nextExclude(exclude, true, false);
    }

}
