package org.zly.utils.collection.list;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * key处理器
 *
 * @param <T> 当前key
 * @param <V> 当前值
 * @param <P> 源数据
 */
public interface ListGroupKeyFunction<T, V, P> {
    T[] apply(T currentKey, V currentValue, Map<T, List<V>> groupedMap, P primaryValues);
}
