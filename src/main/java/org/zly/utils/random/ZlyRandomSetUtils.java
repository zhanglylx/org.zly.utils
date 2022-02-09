package org.zly.utils.random;

import org.apache.commons.lang3.ArrayUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zly
 * @version 1.0
 * @date 2021/7/19 21:37
 */
public class ZlyRandomSetUtils {

    public static int nextIndex(Map<?, ?> map) {
        return ZlyRandomNumberUtils.nextInt(0, map.size());
    }

    public static int nextIndex(List<?> list) {
        return ZlyRandomNumberUtils.nextInt(0, list.size());
    }

//    @SafeVarargs
    public static <T> int nextIndex(T[] o) {
        return ZlyRandomNumberUtils.nextInt(0, o.length);
    }


    public static <T> T nextValue(List<T> list) {
        return list.get(nextIndex(list));
    }

    public static <K, V> V nextValue(Map<K, V> map) {
        int i = 0;
        int index = nextIndex(map);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (i == index) return entry.getValue();
        }
        throw new RuntimeException();
    }


    public static <T> T nextValue(T[] t) {
        return t[nextIndex(t)];
    }

    public static <T> T nextExclude(T t, T[] ts) {
        ts = ArrayUtils.removeAllOccurrences(ts, t);
        return ZlyRandomSetUtils.nextValue(ts);
    }

//    public static Object nextValues(Object... o) {
//        return ZlySetUtils.nextValue(o);
//    }
}
