package org.zly.utils.uiauto.selenium;

import org.openqa.selenium.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ElementUtils {
    /**
     * 判断某个元素是否存在
     */
    public boolean isExistElement(WebDriver webDriver, By by) {
        try {
            webDriver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }




    public static void main(String[] args) throws IOException {
        File file = new File("t.txt");
        File f = new File("444.txt");
        file.renameTo(f);
    }

}
