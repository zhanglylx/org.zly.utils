package org.zly.utils.collection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;

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

    public static <T, V extends Comparable<V>> Comparator<T> creatAscDescComparator(Function<T, V> function, boolean desc) {
        return new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                if (desc) return function.apply(o2).compareTo(function.apply(o1));
                return function.apply(o1).compareTo(function.apply(o2));
            }
        };
    }

    public static <T, G> List<T> createGroupAndComparator(List<T> metadata, Function<T, G> groupKey, Comparator<G> groupComparator, Comparator<T> valueComparator) {
        if (metadata == null || metadata.isEmpty()) throw new IllegalArgumentException("元数据不能为空");
        TreeMap<G, List<T>> treeMap = groupComparator != null ? new TreeMap<>(groupComparator) : new TreeMap<>();
        treeMap.putAll(ZlyListFilterUtils.listGroupOf(metadata, groupKey));
        List<T> list = new ArrayList<>();
        treeMap.values().forEach(new Consumer<List<T>>() {
            @Override
            public void accept(List<T> ts) {
                if (valueComparator != null) ts.sort(valueComparator);
                list.addAll(ts);
            }
        });
        return list;
    }

    public static <T> Comparator<T> createGroupAndComparator(Comparator<T>... comparables) {
        return new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                if (comparables == null || comparables.length == 0) return 0;
                for (Comparator<T> comparable : comparables) {
                    final int compare = comparable.compare(o1, o2);
                    if (compare != 0) return compare;
                }
                return 0;
            }
        };
    }
}
