package org.zly.utils.uiauto.selenium;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.zly.utils.collection.list.ZlyListFilterUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author zhanglianyu
 * @date 2022-08-10 15:14
 */
public class ElementFindUtils {

    /**
     * 通过文本查找元素
     *
     * @param list     元素源
     * @param expected 需要查找的结果
     * @return 符合条件的第一个元素
     * @throws NoSuchElementException 如果没找到抛出
     */
    public static WebElement findByText(List<WebElement> list, String expected) {
        return findByText(list, expected, 0);
    }

    /**
     * @param list
     * @param expected
     * @param index
     * @return 返回查找到的元素
     * @throws NoSuchElementException 如果没找到抛出
     */
    public static WebElement findByText(List<WebElement> list, String expected, int index) {
        try {
            return findByCustom(list, new Predicate<WebElement>() {
                @Override
                public boolean test(WebElement webElement) {
                    return webElement.getText().equals(expected);
                }
            }, index);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("没有找到符合条件的元素[" + expected + "],索引下标[" + index + "]");

        }
    }

    /**
     * @param list
     * @param expected
     * @return 返回查找到的元素
     * @throws NoSuchElementException 如果没找到抛出
     */
    public static List<WebElement> findByTexts(List<WebElement> list, String expected) {
        return findByCustoms(list, new Predicate<WebElement>() {
            @Override
            public boolean test(WebElement webElement) {
                return webElement.getText().equals(expected);
            }
        });
    }

    public static WebElement findByAttribute(List<WebElement> list, String attributeName, String attributeValue) {
        return findByAttribute(list, attributeName, attributeValue, 0);
    }


    public static WebElement findByAttribute(List<WebElement> list, String attributeName, String attributeValue, int index) {
        return findByAttribute(list, ArrayUtils.toArray(attributeName), ArrayUtils.toArray(attributeValue), index);
    }

    public static WebElement findByAttribute(List<WebElement> list, String[] attributeName, String[] attributeValue, int index) {
        if (attributeName.length != attributeValue.length) throw new IllegalArgumentException("key 和value 不相等");
        try {
            return findByCustom(list, new Predicate<WebElement>() {
                @Override
                public boolean test(WebElement webElement) {
                    for (int i = 0; i < attributeName.length; i++) {
                        if (!attributeValue[i].equals(webElement.getAttribute(attributeName[i]))) return false;
                    }
                    return true;
                }
            }, index);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("没有找到符合条件的元素,属性[" + Arrays.toString(attributeName) + "],值 [" + Arrays.toString(attributeName) + "],索引下标[" + index + "]");
        }
    }

    public static List<WebElement> findByAttributes(List<WebElement> list, String attributeName, String attributeValue) {
        return findByAttributes(list, ArrayUtils.toArray(attributeName), ArrayUtils.toArray(attributeValue));
    }

    public static List<WebElement> findByAttributes(List<WebElement> list, String[] attributeName, String[] attributeValue) {
        if (attributeName.length != attributeValue.length) throw new IllegalArgumentException("key 和value 不相等");
        return findByCustoms(list, new Predicate<WebElement>() {
            @Override
            public boolean test(WebElement webElement) {
                for (int i = 0; i < attributeName.length; i++) {
                    if (!attributeValue[i].equals(webElement.getAttribute(attributeName[i]))) return false;
                }
                return true;
            }
        });
    }

    public static WebElement findByCustom(List<WebElement> list, Predicate<WebElement> consume, int index) {
        return findByCustoms(list, consume).get(index);
    }

    public static List<WebElement> findByCustoms(List<WebElement> list, Predicate<WebElement> consume) {
        List<WebElement> webElement = ZlyListFilterUtils.findElements(list, consume);
        if (webElement.isEmpty()) throw new NoSuchElementException("没有找到查找的元素");
        return webElement;
    }

}
