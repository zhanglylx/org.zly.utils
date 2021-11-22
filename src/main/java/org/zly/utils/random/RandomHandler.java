package org.zly.utils.random;

public interface RandomHandler<T> {
    T nextRandom();

    default int size() {
        return 1;
    }
}
