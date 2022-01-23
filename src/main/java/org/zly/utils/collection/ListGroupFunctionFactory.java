package org.zly.utils.collection;

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ListGroupFunctionFactory {
    public static <T> ListGroupKeyFunction<String, T, List<T>> createStrKeyContains(Function<T, String> function) {
        ListGroupKeyFunction<String, T, List<T>> listGroupFunction =
                new ListGroupKeyFunction<String, T, List<T>>() {
                    @Override
                    public String[] apply(String currentKey, T currentValue, Map<String, List<T>> map, List<T> p) {
                        //               用于记录将已经存在的值复制到currentKey的临时map1,为了保持顺序，使用treeMap
                        Map<Integer, T> map1 = new TreeMap<>();
                        //                        循环遍历当前所有的值中是否包含了现在的key，如果包含现在的key，证明这个值应该复制一份到当前key下
//                        如果currentValue包含已存在的key，那么currentValue应该也要复制到包含的key中
                        map.forEach(new BiConsumer<String, List<T>>() {
                            @Override
                            public void accept(String s, List<T> recordsDTOS) {
                                for (T r : recordsDTOS) {
                                    final String apply = function.apply(r);
                                    if (apply == null) continue;
                                    if (apply.contains(currentKey)) {
                                        if (!map1.containsValue(r)) {
                                            map1.put(p.indexOf(r), r);
                                        }
                                    }
                                }
                                final String currentValueKey = function.apply(currentValue);
                                if (currentValueKey != null && currentValueKey.contains(s)) {
                                    recordsDTOS.add(currentValue);
                                }
                            }
                        });
                        if (!map1.isEmpty()) map.put(currentKey, new ArrayList<>(map1.values()));
                        return ArrayUtils.toArray(currentKey);
                    }
                };
        return listGroupFunction;
    }


    public static <T, P> ListGroupKeyFunction<String, T, List<P>> createStrKeyCommaSplit() {
        return
                new ListGroupKeyFunction<String, T, List<P>>() {

                    @Override
                    public String[] apply(String currentKey, T currentValue, Map<String, List<T>> map, List<P> p) {
                        if (currentKey != null) return currentKey.split(",");
                        return ArrayUtils.toArray(currentKey);
                    }
                };
    }


}
