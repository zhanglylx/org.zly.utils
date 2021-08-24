package org.zly.utils.adb.shell;


import org.zly.utils.adb.AdbCode;
import org.zly.utils.adb.Keycode;

/**
 * @author zly
 * @version 1.0
 * @date 2021/1/23 21:10
 */
public interface Input extends AdbCode {

    default String init() {
        return "adb shell input";
    }

    default void keyevent(Keycode keycode) {
    }

    ;

    default void tap(int x, int y) {

    }

    /**
     * @param x
     * @param y
     * @param endX
     * @param endY
     * @param time 单位:毫秒
     */
    default void swip(int x, int y, int endX, int endY, int time) {
    }

    default void swip(int x, int y, int endX, int endY) {
    }

    default String demo(int x, int y) {
        return "sssssss";
    }
}
