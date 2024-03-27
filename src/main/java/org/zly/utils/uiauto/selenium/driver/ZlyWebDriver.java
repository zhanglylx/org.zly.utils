package org.zly.utils.uiauto.selenium.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author zhanglianyu
 * @date 2023-05-06 9:47
 */
public interface ZlyWebDriver extends WebDriver {
    default List<WebElement> findElements(By by, String message) {
        return this.findElements(by);
    }

    default WebElement findElement(By by, String message) {
        return this.findElement(by);
    }
}
