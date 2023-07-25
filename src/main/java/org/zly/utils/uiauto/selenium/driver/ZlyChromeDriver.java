package org.zly.utils.uiauto.selenium.driver;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

/**
 * 张连宇的谷歌driver。主要是用于在动态反射时必须当前类实现webdriver接口
 * @author zhanglianyu
 * @date 2023-05-05 18:43
 */
@Slf4j
public class ZlyChromeDriver extends org.openqa.selenium.chrome.ChromeDriver implements ZlyWebDriver {
    public ZlyChromeDriver() {
    }

    public ZlyChromeDriver(ChromeDriverService service) {
        super(service);
    }

    public ZlyChromeDriver(Capabilities capabilities) {
        super(capabilities);
    }

    public ZlyChromeDriver(ChromeOptions options) {
        super(options);
    }

    public ZlyChromeDriver(ChromeDriverService service, ChromeOptions options) {
        super(service, options);
    }

    public ZlyChromeDriver(ChromeDriverService service, Capabilities capabilities) {
        super(service, capabilities);
    }

}
