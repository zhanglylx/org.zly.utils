package org.zly.utils.collection.map;

import java.util.function.BiFunction;

/**
 * @author zhanglianyu
 * @date 2022-05-14 10:51
 */
public interface MapPutFunction<V> extends BiFunction<V, V, V> {
    @Override
    V apply(V currentValue, V alreadyExistValue);
}
