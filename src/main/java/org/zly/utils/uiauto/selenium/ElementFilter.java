package org.zly.utils.uiauto.selenium;


import lombok.Data;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        return ElementFindUtils.findByText(this.webDriver.findElements(by), expected, index);
    }

    public WebElement byAttribute(By by, String attributeName, String attributeValue) {
        return ElementFindUtils.findByAttribute(this.webDriver.findElements(by), attributeName, attributeValue, 0);
    }

    /**
     * 通过属性查找
     *
     * @param by
     * @param attributeName
     * @param attributeValue
     * @param index
     * @return
     */
    public WebElement byAttribute(By by, String attributeName, String attributeValue, int index) {
        return ElementFindUtils.findByAttribute(this.webDriver.findElements(by), attributeName, attributeValue, index);
    }

    public WebElement byAttributeWait(By by, String attributeName, String attributeValue) {
        return byAttributeWait(by, attributeName, attributeValue, 0);
    }


    public WebElement byAttributeWait(By by, String attributeName, String attributeValue, int index) {
        return this.byCustomWait(by, new Predicate<WebElement>() {
            @Override
            public boolean test(WebElement webElement) {
                return webElement.getAttribute(attributeName).equals(attributeValue);
            }
        }, index);
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
        return ElementFindUtils.findByCustom(this.webDriver.findElements(by), consume, index);
    }

    public WebElement byCustom(By by, Predicate<WebElement> consume) {
        return ElementFindUtils.findByCustom(this.webDriver.findElements(by), consume, 0);
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


}
