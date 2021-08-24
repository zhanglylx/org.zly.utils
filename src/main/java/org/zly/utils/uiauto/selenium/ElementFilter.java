package org.zly.utils.uiauto.selenium;


import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.zly.utils.ListFilter;

import java.sql.Driver;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@Data
public class ElementFilter {
    private RemoteWebDriver webDriver;
    private WebDriverWait webDriverWait;


    public ElementFilter(RemoteWebDriver webDriver, WebDriverWait webDriverWait) {
        this.webDriver = webDriver;
        this.webDriverWait = webDriverWait;
    }

    public boolean isExistElementByText(By by, String expected) {
        return isExistElementByText(by, expected, 0);
    }

    public boolean isExistElementByText(By by, String expected, int index) {
        try {
            byTextWait(by, expected, index);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isExistElementByCustom(By by, Predicate<WebElement> consume) {
        return isExistElementByCustom(by, consume, 0);
    }

    public boolean isExistElementByCustom(By by, Predicate<WebElement> consume, int index) {
        try {
            byCustomWait(by, consume, index);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    /**
     * @param by
     * @param expected
     * @return
     * @throws NoSuchElementException 如果没找到抛出
     */
    public WebElement byText(By by, String expected) {
        return byText(by, expected, 0);
    }

    /**
     * @param by
     * @param expected
     * @param index
     * @return
     * @throws NoSuchElementException 如果没找到抛出
     */
    public WebElement byText(By by, String expected, int index) {
        return byText(this.webDriver.findElements(by), expected, index);
    }

    /**
     * 通过元素text查找，如果查找不到，则报错
     *
     * @param by
     * @param expected
     * @return 符合的第一个元素, 未找到返回null
     * @throws NoSuchElementException 如果没找到抛出
     */
    public WebElement byTextWait(By by, String expected) {
        return byTextWait(by, expected, 0);
    }

    /**
     * 通过元素text查找，如果查找不到，则报错
     *
     * @param by
     * @param expected
     * @param index
     * @return 符合的第 {@code index}个元素
     * @throws NoSuchElementException 如果没找到抛出
     */
    public WebElement byTextWait(By by, String expected, int index) {
        return this.byCustomWait(by, new Predicate<WebElement>() {
            @Override
            public boolean test(WebElement webElement) {
                return webElement.getText().equals(expected);
            }
        }, index);

    }


    public WebElement byCustom(By by, Predicate<WebElement> consume, int index) {
        return byCustom(this.webDriver.findElements(by), consume, index);
    }

    public WebElement byCustom(By by, Predicate<WebElement> consume) {
        return byCustom(this.webDriver.findElements(by), consume, 0);
    }


    public WebElement byCustomWait(By by, Predicate<WebElement> consume) {
        return byCustomWait(by, consume, 0);
    }

    public WebElement byCustomWait(By by, Predicate<WebElement> consume, int index) {
        try {
            return this.webDriverWait.until(new Function<WebDriver, WebElement>() {
                @Override
                public WebElement apply(WebDriver webDriver) {
                    return byCustom(by, consume, index);
                }
            });
        } catch (TimeoutException exception) {
            throw new NoSuchElementException("查找的元素[" + by.toString() + "]" + ",索引坐标[" + index + "]");
        }
    }


    /**
     * 通过文本查找元素
     *
     * @param list     元素源
     * @param expected 需要查找的结果
     * @return 符合条件的第一个元素
     * @throws NoSuchElementException 如果没找到抛出
     */
    public static WebElement byText(List<WebElement> list, String expected) {
        return byText(list, expected, 0);
    }

    /**
     * @param list
     * @param expected
     * @param index
     * @return 返回查找到的元素
     * @throws NoSuchElementException 如果没找到抛出
     */
    public static WebElement byText(List<WebElement> list, String expected, int index) {
        try {
            return byCustom(list, new Predicate<WebElement>() {
                @Override
                public boolean test(WebElement webElement) {
                    return webElement.getText().equals(expected);
                }
            }, index);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("没有找到符合条件的元素[" + expected + "],索引下标[" + index + "]");

        }
    }

    public static WebElement byCustom(List<WebElement> list, Predicate<WebElement> consume, int index) {
        WebElement webElement = ListFilter.findElement(list, new Predicate<WebElement>() {

            @Override
            public boolean test(WebElement webElement) {
                return consume.test(webElement);
            }
        }, index);
        if (webElement == null) throw new NoSuchElementException("没有找到查找的元素");
        return webElement;
    }

}
