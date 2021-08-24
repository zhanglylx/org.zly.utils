package org.zly.utils.uiauto.selenium;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WindowSwitching {
    private RemoteWebDriver webDriver;
    //    key为WindowHandle，值为页面标题
//    暂时只使用到了key，标题为以后扩展预留
    private final Set<String> newLabelHandler;

    private ElementFilter elementFilter;

    private String loopbackHandler;

    public WindowSwitching(RemoteWebDriver webDriver, ElementFilter elementFilter) {
        this.webDriver = webDriver;
        this.newLabelHandler = new HashSet<>();
        this.elementFilter = elementFilter;
    }

    public void loopbackBeforeSwitching() {
        this.loopbackHandler = this.webDriver.getWindowHandle();
    }

    public void loopbackLaterSwitching() {
        Objects.requireNonNull(loopbackHandler);
        this.webDriver.switchTo().window(this.loopbackHandler);
        this.loopbackHandler = null;
    }

    public void loopbackLabelHandler(Consumer<RemoteWebDriver> consumer) {
        loopbackBeforeSwitching();
        consumer.accept(this.webDriver);
        loopbackLaterSwitching();
    }


    public void newLabelBeforeSwitching() {
        if (!this.newLabelHandler.addAll(this.webDriver.getWindowHandles())) {
            throw new RuntimeException();
        }
    }

    public void newLabelHandler(Consumer<RemoteWebDriver> consumer) {
        newLabelBeforeSwitching();
        consumer.accept(this.webDriver);
        newLabelLaterSwitching();
    }


    public void iframeByUrl(String urlRegex) {
        this.webDriver.switchTo().frame(
                elementFilter.byCustomWait(By.tagName("iframe"), new Predicate<WebElement>() {
                    @Override
                    public boolean test(WebElement webElement) {
                        Pattern p = Pattern.compile(urlRegex);
                        // 现在创建 matcher 对象
                        Matcher m = p.matcher(webElement.getAttribute("src"));
                        return m.find();
                    }
                })
        );
    }

    public void iframeByUrlLoopback(Consumer<RemoteWebDriver> consumer, String urlRegex) {
        iframeByUrlLoopback(consumer, urlRegex, true);
    }

    public void iframeByUrlLoopback(Consumer<RemoteWebDriver> consumer, String urlRegex, boolean defaultContent) {
        iframeLoopback(new Consumer<RemoteWebDriver>() {
            @Override
            public void accept(RemoteWebDriver remoteWebDriver) {
                iframeByUrl(urlRegex);
                consumer.accept(remoteWebDriver);
            }
        }, defaultContent);
    }

    public void iframeLoopback(Consumer<RemoteWebDriver> consumer, boolean defaultContent) {
        consumer.accept(this.webDriver);
        if (defaultContent) {
            this.webDriver.switchTo().defaultContent();
        } else {
            this.webDriver.switchTo().parentFrame();
        }
    }


    public void newLabelLaterSwitching() {
        if (this.newLabelHandler.isEmpty()) throw new RuntimeException("没有执行beforeSwitching()");
        String windows = this.webDriver.getWindowHandle();
        this.webDriver.getWindowHandles().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (!newLabelHandler.contains(s)) webDriver.switchTo().window(s);
            }
        });
        if (this.webDriver.getWindowHandle().equals(windows)) throw new RuntimeException("切换窗口失败");
        this.newLabelHandler.clear();
    }

    public void switchToTitle(String regex) {
        this.webDriver.getWindowHandles().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                String currHandles = webDriver.getWindowHandle();
                if (!webDriver.getTitle().matches(regex))
                    webDriver.switchTo().window(s);
            }
        });
    }

    public void openNewLabel(String url) {
        if (StringUtils.isBlank(this.webDriver.getWindowHandle())) {
            this.webDriver.get(url);
        }else{
            this.newLabelHandler(new Consumer<RemoteWebDriver>() {
                @Override
                public void accept(RemoteWebDriver remoteWebDriver) {
                    remoteWebDriver.executeScript("window.open(\"" + url + "\");");
                }
            });
        }

    }

}
