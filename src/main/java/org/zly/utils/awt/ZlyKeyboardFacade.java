package org.zly.utils.awt;

import org.apache.commons.lang3.StringUtils;
import org.zly.utils.awt.ZlyKeyboardHandler;
import org.zly.utils.io.ZlyWindosUtils;

import java.awt.event.KeyEvent;

/**
 * @author zhanglianyu
 * @date 2022-08-29 16:15
 */
public class ZlyKeyboardFacade {
    public static int INTERVAL_TIME = 500;

    public static void downAndEnter() {
        downAndEnter(1);
    }

    public static void downAndEnter(int downNumber) {
        downAndEnter(downNumber, INTERVAL_TIME);
    }

    public static void downAndEnter(int downNumber, int intervalTimeMs) {
        downAndEnter(downNumber, 1, intervalTimeMs);
    }

    public static void downAndEnter(int downNumber, int enterNumber, int intervalTimeMs) {
        for (int i = 0; i < downNumber; i++) {
            ZlyKeyboardHandler.key(KeyEvent.VK_DOWN, INTERVAL_TIME);
        }
        for (int i = 0; i < enterNumber; i++) {
            enter(intervalTimeMs);
        }
    }

    public static void enter() {
        enter(INTERVAL_TIME);
    }

    public static void enter(int intervalTimeMs) {
        ZlyKeyboardHandler.key(KeyEvent.VK_ENTER, intervalTimeMs);
    }

    public static void copy(int delayMs) {
        ZlyKeyboardHandler.getRobot().keyPress(KeyEvent.VK_CONTROL);
        ZlyKeyboardHandler.getRobot().keyPress(KeyEvent.VK_V);
        ZlyKeyboardHandler.getRobot().keyRelease(KeyEvent.VK_V);
        ZlyKeyboardHandler.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        ZlyKeyboardHandler.getRobot().delay(delayMs);
    }

    public static void inputString(String str) {
        if (StringUtils.isBlank(str)) return;
        ZlyAwtUtils.copyToClipboard(str);
        copy();
    }

    public static void copy() {
        copy(INTERVAL_TIME);
    }
}
