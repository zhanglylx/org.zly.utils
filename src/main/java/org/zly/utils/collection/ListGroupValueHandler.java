package org.zly.utils.collection;

import java.util.List;

public interface ListGroupValueHandler<L, V> {
    default List<V> before(List<V> values, L currentData) {
        return values;
    }


    default List<V> after(List<V> values, L currentData) {
        return values;
    }
}
