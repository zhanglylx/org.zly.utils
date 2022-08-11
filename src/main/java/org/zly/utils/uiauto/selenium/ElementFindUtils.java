package org.zly.utils.uiauto.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.zly.utils.collection.list.ZlyListFilterUtils;

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

    public static WebElement findByAttribute(List<WebElement> list, String attributeName, String attributeValue) {
        return findByAttribute(list, attributeName, attributeValue, 0);
    }

    public static WebElement findByAttribute(List<WebElement> list, String attributeName, String attributeValue, int index) {
        try {
            return findByCustom(list, new Predicate<WebElement>() {
                @Override
                public boolean test(WebElement webElement) {
                    return webElement.getAttribute(attributeName).equals(attributeValue);
                }
            }, index);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("没有找到符合条件的元素,属性[" + attributeName + "],值 [" + attributeName + "],索引下标[" + index + "]");

        }
    }

    public static WebElement findByCustom(List<WebElement> list, Predicate<WebElement> consume, int index) {
        WebElement webElement = ZlyListFilterUtils.findElement(list, new Predicate<WebElement>() {

            @Override
            public boolean test(WebElement webElement) {
                return consume.test(webElement);
            }
        }, index);
        if (webElement == null) throw new NoSuchElementException("没有找到查找的元素");
        return webElement;
    }

}
