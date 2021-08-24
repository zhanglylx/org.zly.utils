package org.zly.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ListFilter {

    /**
     * 筛选list，过滤出符合条件的元素
     *
     * @param elements  原始数组
     * @param predicate 过滤器
     * @return
     */
    public static <T> List<T> findElements(List<T> elements, Predicate<T> predicate) {
        return elements.stream().filter(predicate).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(RandomUtils.nextLong());
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        for (int i = 0; i < 100000000; i++) {
            list.add(i + "");
        }
        list.add("sdfsdf");
        long time = System.currentTimeMillis();
        String list2 = findElement(list, new Predicate<String>() {

            @Override
            public boolean test(String s) {
                return s.equals("sdfsdf");
            }
        });
        System.out.println("第一个时间:" + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {

        }
        list.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (s.equals("2")) System.out.println("--");
            }
        });
        System.out.println("第二个时间" + (System.currentTimeMillis() - time));


    }

    /**
     * 查找元素
     *
     * @param elements  源数据
     * @param predicate 筛选器
     * @return 符合条件的第一个元素，未找到则返回null
     */
    public static <T> T findElement(List<T> elements, Predicate<T> predicate) {
        Optional<T> first = elements.stream().filter(predicate).findFirst();
        return first.orElse(null);
    }

    /**
     * 过滤列表，返回查找到的元素
     *
     * @param elements  源列表
     * @param predicate 过滤器
     * @param index     第几个，0开始
     * @param <T>       元素类型
     * @return 返回找到的元素，未找到返回 null
     */
    public static <T> T findElement(List<T> elements, Predicate<T> predicate, int index) {

        List<T> list = elements.stream().filter(predicate).collect(Collectors.toList());
        return list.size() < index+1 ? null : list.get(index);
    }

}
