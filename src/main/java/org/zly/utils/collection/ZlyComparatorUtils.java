package org.zly.utils.collection;

import java.util.Comparator;
import java.util.function.Function;

/**
 * @author zhanglianyu
 * @date 2022-08-26 16:57
 */
public class ZlyComparatorUtils {

    public static <T extends Comparable<T>> Comparator<T> creatAscDescComparator(boolean desc) {
        return new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                if (desc) return o2.compareTo(o1);
                return o1.compareTo(o2);
            }
        };
    }
}
