package org.zly.utils.function;

/**
 * @author zhanglianyu
 * @date 2022-02-08 14:27
 */
public interface ThreeConsumer<T, U,E> {
    void accept(T t, U u,E e);
}
