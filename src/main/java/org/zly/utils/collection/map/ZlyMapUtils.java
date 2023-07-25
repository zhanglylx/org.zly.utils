package org.zly.utils.collection.map;

import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

public class ZlyMapUtils {

    /**
     * <p>检查maps中是否存在空<p/>
     * <pre>
     * MapUtils.isAnyBlank(null)    = true
     * MapUtils.isAnyBlank({"",""},null)  = true
     * MapUtils.isAnyBlank(Map[0])      = true
     * </pre>
     *
     * @param maps 检查的map
     * @return 只要有一个map是Null获取size等于0则返回true
     */
    public static boolean isAnyBlank(Map<?, ?>... maps) {
        if (maps == null || maps.length == 0) return true;
        for (Map<?, ?> entry : maps) {
            if (isBlank(entry)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> map1 = new HashMap<>();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1开始");
                putSafe(1, 1, map, null);
                System.out.println("t1结束");
            }
        });
        t1.start();
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t2开始");
                putSafe(1, 1, map, null);
                System.out.println("t2结束");
            }
        });
        t2.start();
    }

    /**
     * @param key
     * @param value
     * @param map
     * @param mapPutFunction
     * @param <T>
     * @param <V>
     */
    @SneakyThrows
    public static synchronized <T, V> void putSafe(T key, V value, Map<T, V> map, MapPutFunction<V> mapPutFunction) {
        Objects.requireNonNull(map);
        System.out.println("开始执行");
        Thread.sleep(10000);
        V temp = map.get(key);
        if (temp == null) {
            temp = value;
        } else {
            if (mapPutFunction != null) temp = mapPutFunction.apply(value, temp);
        }
        map.put(key, temp);
    }

    /**
     * 检查map是否为空
     *
     * @param map
     * @return 为空获取size=0 返回true
     */
    public static boolean isBlank(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

}

