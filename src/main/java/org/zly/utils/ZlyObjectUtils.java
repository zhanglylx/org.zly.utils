package org.zly.utils;

import java.util.Objects;

/**
 * @author zhanglianyu
 * @date 2022-06-07 11:53
 */
public class ZlyObjectUtils {

    /**
     * 判断目标匹配源
     * @param target
     * @param sources
     * @return  有一个匹配则为true
     */
    public static boolean equalsAnyOne(Object target, Object... sources) {
        boolean b = Objects.equals(target, sources);
        if (b) return true;
        if(sources == null)return false;
        for (Object source : sources) {
            if (Objects.equals(target, source)) return true;
        }
        return false;
    }
}
