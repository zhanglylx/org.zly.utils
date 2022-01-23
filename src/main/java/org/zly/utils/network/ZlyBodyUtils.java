package org.zly.utils.network;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.zly.utils.ZlyReflectUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * @author zly
 * @version 1.0
 * @date 2021/8/9 20:33
 */
public class ZlyBodyUtils {

    private static final Map<Class<?>, List<String>> BODY_AUTOWIRED_MAPS = new ConcurrentHashMap<>();


    public static boolean bodyAutowiredAnnotationExist(Class<?> clazz) {
        return bodyAutowired(clazz, "").isEmpty();
    }

    private static final Object lock = new Object();

    /**
     * 通过反射查找带有@BodyAutowired 注解的方法，通过方法的参数名称和body传入biConsumer
     *
     * @param clazz      需要查找的类
     * @param biConsumer 消费者
     * @param body       与@BodyAutowired相同顺序的参数
     * @BodyExclude 将排除参数
     * @warning 方法的参数顺序必须与body顺序一致，否则将造成参数不匹配现象，如果body为null则传给消费者为null，如果body小于参数，则后续传入null
     */
    public static void bodyAutowired(Class<?> clazz, BiConsumer<String, Object> biConsumer, Object... body) {
        List<String> parametersNames = BODY_AUTOWIRED_MAPS.get(clazz);
        if (parametersNames == null) {
            synchronized (lock) {
                parametersNames = BODY_AUTOWIRED_MAPS.get(clazz);
                if (parametersNames == null) {
                    parametersNames = new ArrayList<>();
                    for (Class<?> aClass : ZlyReflectUtils.getClassAlList(clazz)) {
                        for (Method method : aClass.getDeclaredMethods()) {
                            BodyAutowired bodyAutowired = method.getAnnotation(BodyAutowired.class);
                            if (bodyAutowired != null) {
                                for (Parameter parameter : method.getParameters()) {
                                    if (!ArrayUtils.contains(bodyAutowired.excludeParameters(), parameter.getName())
                                            && parameter.getAnnotation(BodyExclude.class) == null) {
                                        parametersNames.add(parameter.getName());
                                    }
                                }
                            }
                        }
                    }
                    BODY_AUTOWIRED_MAPS.put(clazz, parametersNames);
                }
            }
        }
        for (int i = 0; i < parametersNames.size(); i++) {
            if (body != null && body.length > i) {
                biConsumer.accept(parametersNames.get(i), body[i]);
            } else {
                biConsumer.accept(parametersNames.get(i), null);
            }
        }
    }

    public static Map<String, Object> bodyAutowired(Class<?> clazz, Object... body) {
        Map<String, Object> map = new HashMap<>();
        bodyAutowired(clazz, map::put, body);
        return map;
    }

    public static <T> T bodyAutowired(BodyType<T> bodyType, Object... body) {
        return bodyType.getHandler(bodyAutowired(bodyType.getTemplateClass(), body));
    }

    public static void main(String[] args) {
//        final ObjectBodyTypeImpl<String,String> stringObjectBodyType = new ObjectBodyTypeImpl<String,String>(){};
//        bodyAutowired(String.class, stringObjectBodyType, null);
//        ObjectBodyTypeImpl<HGHG> bodyTypeImpl = new ObjectBodyTypeImpl<HGHG>(ZlyBodyUtils.class){};
//        System.out.println(bodyAutowired(bodyTypeImpl,99).getFff());
    }

    @BodyAutowired
    public void sssss(String fff) {

    }
}
