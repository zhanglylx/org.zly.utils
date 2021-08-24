package org.zly.utils.DynamicSplicing;

import java.util.List;

/**
 * 动态拼接接口
 *
 * @author zly
 * @version 1.0
 * @date 2021/1/26 15:12
 */
public interface DynamicSplicing<T> {
    default T getResult() {
        return null;
    }

    default DynamicSplicing<T> join(Object... code) {
        throw new UnsupportedOperationException();
    }

    default void insert(int location, T... value) {
        throw new UnsupportedOperationException();
    }

    /**
     * 删除指定位置的内容
     *
     * @param location 坐标，从0开始
     * @return 被删除的元素
     */
    default T remove(int location) {
        throw new UnsupportedOperationException();
    }

    /**
     *  删除从指定位置到结束位置之间的内容
     * @param start  坐标，开始位置，从0开始
     * @param end    结束坐标，不包含end
     * @return 被删除的内容数组
     */
    default T[] remove(int start, int end) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取指定位置的value
     *
     * @param location
     * @return
     */
    default T get(int location) {
        throw new UnsupportedOperationException();
    }

    default T[] get(int start, int end) {
        throw new UnsupportedOperationException();
    }

    default T init() {
        throw new UnsupportedOperationException();
    }

    default T clear() {
        throw new UnsupportedOperationException();
    }

    default Object separator() {
        throw new UnsupportedOperationException();
    }

    /**
     * 忽略需要拼接的方面名称，如果配置了全局，这里不需要配置
     *
     * @return
     */
    default List<String> ignoreMethonName() {
        return null;
    }

    /**
     * 忽略全局拼接方法名称
     *
     * @return
     */
    default boolean ignoreGlobalMethonName() {
        return false;
    }

    /**
     * 查找指定内容的索引坐标
     *
     * @param t
     * @return 坐标从0开始，返回第一次出现的位置，-1代表没有扎到
     */
    default int indexOf(T t) {
        throw new UnsupportedOperationException();
    }


}
