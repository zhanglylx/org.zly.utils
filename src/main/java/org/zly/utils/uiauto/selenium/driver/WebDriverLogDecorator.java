package org.zly.utils.uiauto.selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zly.utils.adb.AdbCodeFactory;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;

/**
 * webdriver日志装饰器
 * 采用反射动态代理处理
 * @author zhanglianyu
 * @date 2023-05-05 15:09
 */
public class WebDriverLogDecorator implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(WebDriverLogDecorator.class);
    private WebDriver webDriver;
    private Consumer<WebElement> findConsumer;

    public WebDriverLogDecorator(WebDriver webDriver) {
        this(webDriver, null);
    }

    public WebDriverLogDecorator(WebDriver webDriver, Consumer<WebElement> findConsumer) {
        this.webDriver = webDriver;
        this.findConsumer = findConsumer;
    }

    @SuppressWarnings("all")
    public <T extends WebDriver> T getInstance() {
        return (T) Proxy.newProxyInstance(
                this.webDriver.getClass().getClassLoader(),
                this.webDriver.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final Object invoke = method.invoke(this.webDriver, args);
        if (method.getName().startsWith("find")) {
            if (invoke instanceof WebElement) {
                WebElement w = (WebElement) invoke;
                log.info("查找到的元素tag:{},text:{}", w.getTagName(), w.getText());
                if (findConsumer != null) findConsumer.accept(w);
            }
        }
        return invoke;
    }
}
