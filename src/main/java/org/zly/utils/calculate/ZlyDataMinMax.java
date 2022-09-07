package org.zly.utils.calculate;

import lombok.Data;
import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 统计最大和最小值
 *
 * @author zhanglianyu
 * @date 2022-08-29 10:33
 */
@Data
public class ZlyDataMinMax<T extends Comparable<T>, V> {
    private T minCompare;
    private V minValue;
    private T maxCompare;
    private V maxValue;
    private Comparator<T> comparator;

    public ZlyDataMinMax() {
        this(null,null);
    }

    public ZlyDataMinMax(T initCompare, V initValue) {
        this(initCompare, initValue, initCompare, initValue, null);
    }

    public ZlyDataMinMax(T minCompare, V minValue, T maxCompare, V maxValue, Comparator<T> comparator) {
        this.minCompare = minCompare;
        this.minValue = minValue;
        this.maxCompare = maxCompare;
        this.maxValue = maxValue;
        this.comparator = comparator;
        if (comparator == null) {
            this.comparator = new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    return o1.compareTo(o2);
                }
            };
        }
    }

    public synchronized void compareTo(T currentCompare, V currentValue) {
        Objects.requireNonNull(currentCompare,"currentCompare is null");
        Objects.requireNonNull(currentValue,"currentValue is null");
        if (minCompare == null) {
            minCompare = currentCompare;
            minValue = currentValue;
            maxCompare = currentCompare;
            maxValue = currentValue;
            return;
        }
        int compare = comparator.compare(minCompare, currentCompare);
        if (compare > 0) {
            minCompare = currentCompare;
            minValue = currentValue;
        }
        compare = comparator.compare(maxCompare, currentCompare);
        if (compare < 0) {
            maxCompare = currentCompare;
            maxValue = currentValue;
        }
    }

    public void compareToCollection(Collection<V> values, Function<V, T> function) {
        values.forEach(new Consumer<V>() {
            @Override
            public void accept(V v) {
                compareTo(function.apply(v), v);
            }
        });
    }

}
