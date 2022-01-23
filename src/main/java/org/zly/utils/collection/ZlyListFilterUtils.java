package org.zly.utils.collection;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;


public class ZlyListFilterUtils {

    /**
     * 筛选list，过滤出符合条件的元素
     *
     * @param elements  原始数组
     * @param predicate 过滤器
     * @return
     */
    public static <T> List<T> findElements(List<T> elements, Predicate<T> predicate) {
        return elements.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * 查找元素
     *
     * @param elements  源数据
     * @param predicate 筛选器
     * @return 符合条件的第一个元素，未找到则返回null
     */
    public static <T> T findElement(List<T> elements, Predicate<T> predicate) {
        Optional<T> first = elements.stream().filter(predicate).findFirst();
        return first.orElse(null);
    }

    /**
     * 过滤列表，返回查找到的元素
     *
     * @param elements  源列表
     * @param predicate 过滤器
     * @param index     第几个，0开始
     * @param <T>       元素类型
     * @return 返回找到的元素，未找到返回 null
     */
    public static <T> T findElement(List<T> elements, Predicate<T> predicate, int index) {

        List<T> list = elements.stream().filter(predicate).collect(Collectors.toList());
        return list.size() < index + 1 ? null : list.get(index);
    }


    public static <K, L> Map<K, List<L>> listGroupOf(List<L> lists, Function<L, K> keyMapper) {
        return listGroupOf(lists, keyMapper, null);
    }

    public static <K, V> Map<K, List<V>> listGroupOf(List<V> lists, Function<V, K> keyMapper, ListGroupKeyFunction<K, V, List<V>> keyHandler) {
        return listGroupOf(lists, keyMapper, null, keyHandler);
    }

    public static <K, U, L> Map<K, List<U>> listGroupOf(List<L> lists, Function<L, K> keyMapper,
                                                        Function<L, U> valueMapper, ListGroupKeyFunction<K, U, List<L>> keyHandler) {
        return listGroupOf(lists, keyMapper, valueMapper, keyHandler, null);
    }

    public static <K, U, L> Map<K, List<U>> listGroupOf(List<L> lists,
                                                        Function<L, K> keyMapper,
                                                        Function<L, U> valueMapper,
                                                        ListGroupKeyFunction<K, U, List<L>> keyHandler,
                                                        Supplier<List<U>> initValueAccessor) {
        return listGroupOf(lists, keyMapper, valueMapper, keyHandler, initValueAccessor, null);
    }

    @SuppressWarnings("unchecked")
    public static <K, U, L> Map<K, List<U>> listGroupOf(List<L> lists,
                                                        Function<L, K> keyMapper,
                                                        Function<L, U> valueMapper,
                                                        ListGroupKeyFunction<K, U, List<L>> keyHandler,
                                                        Supplier<List<U>> initValueAccessor,
                                                        ListGroupValueHandler<L,U> listGroupValueHandler) {
        Objects.requireNonNull(lists);
        Map<K, List<U>> map = new LinkedHashMap<>();

        valueMapper = ObjectUtils.firstNonNull(valueMapper, new Function<L, U>() {
            @Override
            public U apply(L l) {
                return (U) l;
            }
        });

        keyHandler = ObjectUtils.firstNonNull(keyHandler, new ListGroupKeyFunction<K, U, List<L>>() {
            @Override
            public K[] apply(K k, U value, Map<K, List<U>> map, List<L> p) {
                return ArrayUtils.toArray(k);
            }
        });
        initValueAccessor = ObjectUtils.firstNonNull(initValueAccessor, ArrayList::new);
        listGroupValueHandler = ObjectUtils.firstNonNull(listGroupValueHandler, new ListGroupValueHandler<L,U>() {
        });
        K key;
        U u;
        for (L l : lists) {
            key = keyMapper.apply(l);
            u = valueMapper.apply(l);
            final K[] apply = keyHandler.apply(key, u, map, lists);
            if (apply == null) continue;
            for (K k : apply) {
                List<U> list = map.get(k);
                if (list == null) {
                    list = initValueAccessor.get();
                    list = listGroupValueHandler.before(list,l);
                    list.add(u);
                    map.put(k, list);
                } else {
                    list = listGroupValueHandler.before(list,l);
                    list.add(u);
                }
                List<U> after = listGroupValueHandler.after(list,l);
                if (after != list) map.put(k, after);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("1");
        list.add("1");
        System.out.println(listGroupOf(list, new Function<String, Object>() {
            @Override
            public Object apply(String s) {
                return s;
            }
        }));
    }
}
