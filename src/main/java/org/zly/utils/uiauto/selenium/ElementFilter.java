package org.zly.utils.uiauto.selenium;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.zly.utils.collection.list.ZlyListFilterUtils;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@Data
@Slf4j
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
        return byTexts(by, expected).get(index);
    }

    public List<WebElement> byTexts(By by, String expected) {
        return ElementFindUtils.findByTexts(this.webDriver.findElements(by), expected);
    }

    public WebElement byAttribute(By by, String attributeName, String attributeValue) {
        return byAttribute(by, attributeName, attributeValue, 0);
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
        return byAttributes(by, attributeName, attributeValue).get(index);
    }

    public List<WebElement> byAttributes(By by, String attributeName, String attributeValue) {
        return ElementFindUtils.findByAttributes(this.webDriver.findElements(by), attributeName, attributeValue);
    }

    public WebElement byAttributeWait(By by, String attributeName, String attributeValue) {
        return byAttributeWait(by, attributeName, attributeValue, 0);
    }


    public WebElement byAttributeWait(By by, String attributeName, String attributeValue, int index) {
        return byAttributeWaits(by, attributeName, attributeValue).get(index);
    }

    public List<WebElement> byAttributeWaits(By by, String attributeName, String attributeValue) {
        return this.byCustomWaits(by, new Predicate<WebElement>() {
            @Override
            public boolean test(WebElement webElement) {
                return attributeValue.equals(webElement.getAttribute(attributeName));
            }
        });
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
        return byTextWaits(by, expected).get(index);
    }

    /**
     * 通过元素text查找，如果查找不到，则报错
     *
     * @param by
     * @param expected
     * @throws NoSuchElementException 如果没找到抛出
     */
    public List<WebElement> byTextWaits(By by, String expected) {
        return this.byCustomWaits(by, new Predicate<WebElement>() {
            @Override
            public boolean test(WebElement webElement) {
                return webElement.getText().equals(expected);
            }
        });
    }

    /**
     * 通过元素text查找，如果查找不到，则报错
     *
     * @param by
     * @param expected
     * @return 符合的第 {@code index}个元素
     * @throws NoSuchElementException 如果没找到抛出
     */
    public WebElement byContainTextWait(By by, String expected) {
        return byContainTextWait(by, expected, 0);
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
    public WebElement byContainTextWait(By by, String expected, int index) {
        return byContainTextWaits(by, expected).get(index);
    }

    public List<WebElement> byContainTextWaits(By by, String expected) {
        return this.byCustomWaits(by, new Predicate<WebElement>() {
            @Override
            public boolean test(WebElement webElement) {
                return webElement.getText().contains(expected);
            }
        });
    }


    public WebElement byCustom(By by, Predicate<WebElement> consume) {
        return byCustom(by, consume, 0);
    }

    public WebElement byCustom(By by, Predicate<WebElement> consume, int index) {
        return ElementFindUtils.findByCustom(this.webDriver.findElements(by), consume, index);
    }

    public List<WebElement> byCustoms(By by, Predicate<WebElement> consume) {
        return ElementFindUtils.findByCustoms(this.webDriver.findElements(by), consume);
    }

    public WebElement byCustomWait(By by, Predicate<WebElement> consume) {
        return byCustomWait(by, consume, 0);
    }

    public WebElement byCustomWait(By by, Predicate<WebElement> consume, int index) {
        try {
            return byCustomWaits(by, consume).get(index);
        } catch (TimeoutException exception) {
            throw new NoSuchElementException("查找的元素[" + by.toString() + "]" + ",索引坐标[" + index + "]");
        }
    }


    public List<WebElement> byCustomWaits(By by, Predicate<WebElement> consume) {
        try {
            final List<WebElement> 本次没有等待到元素 = this.webDriverWait.until(new Function<WebDriver, List<WebElement>>() {
                @Override
                public List<WebElement> apply(WebDriver webDriver) {
                    try {
                        return byCustoms(by, consume);
                    } catch (RuntimeException e) {
                        log.debug("本次没有等待到元素", e);
                        return null;
                    }
                }
            });
            return 本次没有等待到元素;
        } catch (TimeoutException exception) {
            throw new NoSuchElementException("查找的元素[" + by.toString() + "]");
        }
    }

}
