package org.zly.utils.random;

public  interface RandomHandler<T> {
    T nextRandom(long size,long  sizeOfRemainingSpace);

    default int size() {
        return 1;
    }


}
