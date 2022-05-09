package org.zly.utils;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.zly.utils.random.RandomDate;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ZlyBeanUtils {

    // 获取控制字段列表
    public static String[] getIfNullPropertyNames(Object target, boolean isNull) {
        final BeanWrapper src = new BeanWrapperImpl(target);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (isNull) {
                if (srcValue == null) {
                    emptyNames.add(pd.getName());
                }
            } else {
                if (srcValue != null) {
                    emptyNames.add(pd.getName());
                }
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static String[] getIsNullPropertyNames(Object target) {
        return getIfNullPropertyNames(target, true);
    }

    public static String[] getIsNotNullPropertyNames(Object target) {
        return getIfNullPropertyNames(target, false);
    }

    @SneakyThrows
    public static void copyPropertiesIgnoreExistValue(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getIsNotNullPropertyNames(target));
    }

    @SneakyThrows
    public static <T> T copyProperties(Object source, Class<T> tClass) {
        final T t = tClass.newInstance();
        copyProperties(source, t);
        return t;
    }

    @SneakyThrows
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
//        final List<Field> sourceDeclaredFields = ZlyReflectUtils.getDeclaredFields(source.getClass(), true);
//        final List<Field> targetDeclaredFields = ZlyReflectUtils.getDeclaredFields(target.getClass(), true);
//        Map<String, Field> targetMap = new HashMap<>();
//        for (Field targetDeclaredField : targetDeclaredFields) {
//            targetMap.put(targetDeclaredField.getName(), targetDeclaredField);
//        }
//        for (Field sourceField : sourceDeclaredFields) {
//            if (sourceField.getType().isAssignableFrom(List.class)) {
//                sourceField.setAccessible(true);
//                final Field targetField = targetMap.get(sourceField.getName());
//                if (targetField == null || !targetField.getType().isAssignableFrom(List.class)) continue;
//                List<?> list = (List<?>) sourceField.get(source);
//                if (list == null) continue;
//                if(targetField.getGenericType() instanceof ParameterizedType){
//                    final ParameterizedType genericType = (ParameterizedType) targetField.getGenericType();
//                    targetField.setAccessible(true);
//                    targetField.set(target,copyPropertiesIsCollection(list,genericType.getRawType().getClass()));
//                }
//            }
//        }

    }

    @SneakyThrows
    public static <T> List<T> copyPropertiesIsCollection(Collection<?> source, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        for (Object o : source) {
            list.add(ZlyBeanUtils.copyProperties(o, clazz));
        }
        return list;
    }

    public static void copyProperties(Object[] sources, Object target) {
        BeanUtils.copyProperties(sources[0], target);
        for (int i = 1; i < sources.length; i++) {
            copyPropertiesIgnoreExistValue(sources, target);
        }
    }

    /**
     * 检查任意一个属性是null
     * @param impl
     * @param containsParentClass
     * @return  有一个属性为空则为true
     */
    public static boolean isPropertiesAnyIsNull(Object impl, boolean containsParentClass) {
        return !isPropertiesNoneIsNotNull(impl, containsParentClass);
    }

    /**
     * 检查所有属性不能存在null
     * @param impl
     * @param containsParentClass
     * @return 所有属性不为空返回true
     */
    public static boolean isPropertiesNoneIsNotNull(Object impl, boolean containsParentClass) {
        final Map<Field, Method> memberPublicMethods = ZlyReflectUtils.getMemberPublicMethods(impl.getClass(), containsParentClass);
        try {
            memberPublicMethods.forEach(new BiConsumer<Field, Method>() {
                @SneakyThrows
                @Override
                public void accept(Field field, Method method) {
                    Objects.requireNonNull(method.invoke(impl));
                }
            });
        } catch (NullPointerException ig) {
            return false;
        }
        return true;
    }
}

