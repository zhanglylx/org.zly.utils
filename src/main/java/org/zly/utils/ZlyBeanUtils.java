package org.zly.utils;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;


import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

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

    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    public static void copyProperties(Object[] sources, Object target) {
        BeanUtils.copyProperties(sources[0], target);
        for (int i = 1; i < sources.length; i++) {
            copyPropertiesIgnoreExistValue(sources, target);
        }
    }
}
