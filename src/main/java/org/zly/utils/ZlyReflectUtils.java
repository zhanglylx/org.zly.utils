package org.zly.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ZlyReflectUtils {
    //成员GET方法
    public static Function<Field, String> MEMBER_GET = s -> "get" + StringUtils.capitalize(s.getName());
    //成员SET方法
    public static Function<Field, String> MEMBER_SET = s -> "set" + StringUtils.capitalize(s.getName());
    //成员is方法
    public static Function<Field, String> MEMBER_IS = s -> "is" + StringUtils.capitalize(s.getName());
    public static List<Function<Field, String>> MEMBER_GET_IS = assemblyFormattings(MEMBER_GET, MEMBER_IS);

    private static final Map<Class<?>, List<Class<?>>> BUFFER_CLASS = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Type[]> GENERIC_TYPES_CACHE = new ConcurrentHashMap<>();

    private static final Object GET_CLASS_ALL_LIST_LOCKGET_CLAS = new Object();

    @SafeVarargs
    public static List<Function<Field, String>> assemblyFormattings(Function<Field, String>... formattings) {
        if (formattings == null) return null;
        return Arrays.asList(formattings);
    }

    public static Map<Field, Method> getMemberPublicMethods(Class<?> clazz, boolean containsParentClass, Class<?>... parameterTypes) {
        return getMemberPublicMethods(clazz, containsParentClass, MEMBER_GET_IS, parameterTypes);
    }

    public static Map<Field, Method> getMemberPublicMethods(Class<?> clazz, boolean containsParentClass, Function<Field, String> formattings, Class<?>... parameterTypes) {
        List<Function<Field, String>> list = new ArrayList<>(1);
        list.add(formattings);
        return getMemberPublicMethods(clazz, containsParentClass, list, parameterTypes);
    }

    public static void getClassAlList(Class<?> clazz, Consumer<Class<?>> consumer) {
        getClassAlList(clazz).forEach(aClass -> consumer.accept(aClass));
    }

    public static List<Class<?>> getClassAlList(Class<?> clazz) {
        List<Class<?>> list = BUFFER_CLASS.get(clazz);
        if (list != null) return list;
        synchronized (GET_CLASS_ALL_LIST_LOCKGET_CLAS) {
            list = BUFFER_CLASS.get(clazz);  // 保存属性对象数组到列表
            if (list != null) return list;
            list = new ArrayList<>();
            BUFFER_CLASS.put(clazz, list);
            while (clazz != null) {  // 遍历所有父类字节码对象
                list.add(0, clazz);
                clazz = clazz.getSuperclass();  // 获得父类的字节码对象
            }
            return list;
        }
    }

    public static List<Field> getDeclaredFields(Class<?> clazz, boolean containsParentClass) {
        if (!containsParentClass) return new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        List<Field> fieldsList = new ArrayList<>();  // 保存属性对象数组到列表
        getClassAlList(clazz, aClass -> fieldsList.addAll(Arrays.asList(aClass.getDeclaredFields())));
        return fieldsList;
    }


    /**
     * 获取class中属性的公共方法
     *
     * @param clazz       实体类
     * @param formattings 格式化的方法名称
     * @return ${clazz}的公共Get方法对象
     */
    public static Map<Field, Method> getMemberPublicMethods(Class<?> clazz, boolean containsParentClass, List<Function<Field, String>> formattings, Class<?>... parameterTypes) {
        Map<Field, Method> methods = new LinkedHashMap<>();
        for (Field field : getDeclaredFields(clazz, containsParentClass)) {
            Method method;
            try {
                for (Function<Field, String> f : formattings) {
                    method = clazz.getMethod(f.apply(field), parameterTypes);
                    if (Modifier.isPublic(method.getModifiers()))
                        methods.put(field, method);
                }
            } catch (NoSuchMethodException ignored) {
            }
        }
        return methods;
    }

    public static Class<?> getGenericClass(Class<?> clazz) {
        return getGenericClass(clazz, 0);
    }

    public static Class<?> getGenericClass(Class<?> clazz, int index) {
        Type type = (getGenericTypes(clazz)[index]);
        try {
            return (Class<?>) (getGenericTypes(clazz)[index]);
        } catch (ClassCastException e) {
            if (type instanceof ParameterizedTypeImpl) {
                return ((ParameterizedTypeImpl) type).getRawType();
            }
        }
        return (Class<?>) type;
    }

    public static Class<?> getGenericClass(Class<?> clazz, int index, Class<?> defaultType) {
        Class<?> c;
        try {
            c = getGenericClass(clazz, index);
            return c;
        } catch (Exception e) {
            return defaultType;
        }
    }

    public static Type[] getGenericTypes(Class<?> clazz) {
        Type[] type = GENERIC_TYPES_CACHE.get(clazz);
        if (type == null) {
            try {
                Type superClass = clazz.getGenericSuperclass();
                type = ((ParameterizedType) superClass).getActualTypeArguments();
            } catch (ClassCastException ignored) {
            }
            if (type == null) {
                try {
//            尽量兼容下,如果类之间实现接口并将泛型指定，而不是通过继承的方式，需要通过此逻辑获取
                    for (Type genericInterface : clazz.getGenericInterfaces()) {
                        type = ArrayUtils.addAll(type, ((((ParameterizedType) genericInterface).getActualTypeArguments())));
                    }
                } catch (ClassCastException ignored) {

                }
            }
        }
        if (type == null) type = new Type[]{};
        GENERIC_TYPES_CACHE.put(clazz, type);
        return type;
    }

}
