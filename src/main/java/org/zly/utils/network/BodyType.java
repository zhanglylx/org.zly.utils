package org.zly.utils.network;

import com.alibaba.fastjson2.TypeReference;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author zly
 * @version 1.0
 * @date 2021/8/11 10:15
 */
public interface BodyType<R> {

    default R getHandler(Map<String, Object> map) {
        throw new UnsupportedOperationException();
    }

    default Class<?> getTemplateClass() {
//        try {
//            Type superClass = getClass().getGenericSuperclass();
//            Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
//            return (Class<E>) type;
//        } catch (ClassCastException ignored) {
//        }
//
//        for (Type genericInterface : getClass().getGenericInterfaces()) {
//            return (Class<E>) (((ParameterizedType) genericInterface).getActualTypeArguments()[0]);
//        }
        throw new IllegalArgumentException("没有找到模板泛型R");
    }
}
