package org.zly.utils.uiauto.selenium;

import org.openqa.selenium.By;

/**
 * @author zhanglianyu
 * @date 2022-08-28 22:02
 */
public class ZlyByUtils {

    public static By tagSpan(){
        return By.tagName("span");
    }

    public static By tagDiv(){
        return By.tagName("div");
    }

    public static By tagInput(){
        return By.tagName("input");
    }

    public static By tagTextarea(){
        return By.tagName("textarea");
    }
}
