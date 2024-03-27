package org.zly.utils.collection.map;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author zhanglianyu
 * @date 2022-05-14 10:51
 */
public interface MapFirstPutFunction<V,R> extends Function<V, R> {
    R apply(V t);
}
