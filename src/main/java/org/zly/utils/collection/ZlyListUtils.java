package org.zly.utils.collection;

import java.util.List;

public class ZlyListUtils {
    /**
     * 获取list中存放的最后一个元素
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T getLastElement(List<T> list) {
        if (list == null || list.isEmpty()) return null;
        return list.get(list.size() - 1);
    }
}
