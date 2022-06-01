package org.zly.utils.collection.list;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.zly.utils.collection.list.ListGroupFunctionFactory.createStrKeyCommaSplit;

public class ListGroupFunctionFacade {
    public static <V> Map<String, List<V>> listGroupOfStrKeyCommaSplit(List<V> lists, Function<V, String> function) {
        return ZlyListFilterUtils.listGroupOf(lists, function, createStrKeyCommaSplit());
    }

    public static <V> Map<String, List<V>> listGroupOfStrKeyContains(List<V> lists, Function<V, String> function) {
        return ZlyListFilterUtils.listGroupOf(lists, function, ListGroupFunctionFactory.createStrKeyContains(function));
    }
}
