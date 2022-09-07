package org.zly.utils.awt;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author zhanglianyu
 * @date 2022-08-15 16:55
 */
public class ZlyKeyboardHandler {
    private static Robot robot;

    static {
        System.setProperty("java.awt.headless", "false");
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static Robot getRobot() {
        return robot;
    }

    public static void key(int keyEvent) {
        key(keyEvent, 0);
    }

    public static void key(int keyEvent, int delayMs) {
        robot.keyPress(keyEvent);
        robot.keyRelease(keyEvent);
        if (delayMs == 0) return;
        robot.delay(delayMs);
    }


    @SneakyThrows
    public static void key(int keyEvent, TimeUnit timeUnit, long time) {
        robot.keyPress(keyEvent);
        robot.keyRelease(keyEvent);
        timeUnit.sleep(time);
    }





    public static void Sr(String[] srz) {
        for (int i = 0; i < srz.length; i++) {
            String sr = srz[i];//将字符串数组一个一个的取出来
            int t1 = 200;
            //Java的robot开始帮我们敲键盘的地方
            //小写字母模块
            switch (sr) {
                case "a":
                    robot.keyPress(KeyEvent.VK_A);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_A);
                    robot.delay((int) t1);
                    break;
                case "b":
                    robot.keyPress(KeyEvent.VK_B);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_B);
                    robot.delay((int) t1);
                    break;
                case "c":
                    robot.keyPress(KeyEvent.VK_C);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_C);
                    robot.delay((int) t1);
                    break;
                case "d":
                    robot.keyPress(KeyEvent.VK_D);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_D);
                    robot.delay((int) t1);
                    break;
                case "e":
                    robot.keyPress(KeyEvent.VK_E);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_E);
                    robot.delay((int) t1);
                    break;
                case "f":
                    robot.keyPress(KeyEvent.VK_F);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_F);
                    robot.delay((int) t1);
                    break;
                case "g":
                    robot.keyPress(KeyEvent.VK_G);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_G);
                    robot.delay((int) t1);
                    break;
                case "h":
                    robot.keyPress(KeyEvent.VK_H);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_H);
                    robot.delay((int) t1);
                    break;
                case "i":
                    robot.keyPress(KeyEvent.VK_I);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_I);
                    robot.delay((int) t1);
                    break;
                case "j":
                    robot.keyPress(KeyEvent.VK_J);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_J);
                    robot.delay((int) t1);
                    break;
                case "k":
                    robot.keyPress(KeyEvent.VK_K);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_K);
                    robot.delay((int) t1);
                    break;
                case "l":
                    robot.keyPress(KeyEvent.VK_L);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_L);
                    robot.delay((int) t1);
                    break;
                case "m":
                    robot.keyPress(KeyEvent.VK_M);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_M);
                    robot.delay((int) t1);
                    break;
                case "n":
                    robot.keyPress(KeyEvent.VK_N);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_N);
                    robot.delay((int) t1);
                    break;
                case "o":
                    robot.keyPress(KeyEvent.VK_O);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_O);
                    robot.delay((int) t1);
                    break;
                case "p":
                    robot.keyPress(KeyEvent.VK_P);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_P);
                    robot.delay((int) t1);
                    break;
                case "q":
                    robot.keyPress(KeyEvent.VK_Q);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_Q);
                    robot.delay((int) t1);
                    break;
                case "r":
                    robot.keyPress(KeyEvent.VK_R);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_R);
                    robot.delay((int) t1);
                    break;
                case "s":
                    robot.keyPress(KeyEvent.VK_S);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_S);
                    robot.delay((int) t1);
                    break;
                case "t":
                    robot.keyPress(KeyEvent.VK_T);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_T);
                    robot.delay((int) t1);
                    break;
                case "u":
                    robot.keyPress(KeyEvent.VK_U);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_U);
                    robot.delay((int) t1);
                    break;
                case "v":
                    robot.keyPress(KeyEvent.VK_V);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_V);
                    robot.delay((int) t1);
                    break;
                case "w":
                    robot.keyPress(KeyEvent.VK_W);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_W);
                    robot.delay((int) t1);
                    break;
                case "x":
                    robot.keyPress(KeyEvent.VK_X);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_X);
                    robot.delay((int) t1);
                    break;
                case "y":
                    robot.keyPress(KeyEvent.VK_Y);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_Y);
                    robot.delay((int) t1);
                    break;
                case "z":
                    robot.keyPress(KeyEvent.VK_Z);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_Z);
                    robot.delay((int) t1);
                    break;
            }

            //大写字母模块
            switch (sr) {
                case "A":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_A);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_A);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "B":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_B);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_B);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "C":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_C);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_C);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "D":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_D);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_D);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "E":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_E);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_E);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "F":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_F);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_F);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "G":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_G);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_G);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "H":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_H);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_H);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "I":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_I);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_I);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "J":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_J);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_J);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "K":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_K);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_K);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "L":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_L);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_L);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "M":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_M);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_M);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "N":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_N);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_N);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "O":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_O);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_O);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "P":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_P);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_P);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "Q":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_Q);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_Q);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "R":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_R);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_R);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "S":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_S);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_S);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "T":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_T);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_T);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "U":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_U);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_U);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "V":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_V);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_V);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "W":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_W);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_W);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "X":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_X);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_X);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "Y":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_Y);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_Y);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "Z":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_Z);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_Z);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
            }

            //数字模块
            switch (sr) {
                case "1":
                    robot.keyPress(KeyEvent.VK_1);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_1);
                    robot.delay((int) t1);
                    break;
                case "2":
                    robot.keyPress(KeyEvent.VK_2);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_2);
                    robot.delay((int) t1);
                    break;
                case "3":
                    robot.keyPress(KeyEvent.VK_3);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_3);
                    robot.delay((int) t1);
                    break;
                case "4":
                    robot.keyPress(KeyEvent.VK_4);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_4);
                    robot.delay((int) t1);
                    break;
                case "5":
                    robot.keyPress(KeyEvent.VK_5);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_5);
                    robot.delay((int) t1);
                    break;
                case "6":
                    robot.keyPress(KeyEvent.VK_6);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_6);
                    robot.delay((int) t1);
                    break;
                case "7":
                    robot.keyPress(KeyEvent.VK_7);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_7);
                    robot.delay((int) t1);
                    break;
                case "8":
                    robot.keyPress(KeyEvent.VK_8);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_8);
                    robot.delay((int) t1);
                    break;
                case "9":
                    robot.keyPress(KeyEvent.VK_9);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_9);
                    robot.delay((int) t1);
                    break;
                case "0":
                    robot.keyPress(KeyEvent.VK_0);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_0);
                    robot.delay((int) t1);
                    break;
            }

            //数字符号模块
            switch (sr) {
                case "!":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_1);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_1);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "@":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_2);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_2);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "#":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_3);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_3);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "$":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_4);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_4);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "%":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_5);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_5);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "^":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_6);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_6);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "&":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_7);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_7);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "*":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_8);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_8);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "(":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_9);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_9);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case ")":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_0);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_0);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
            }

            //符号模块
            switch (sr) {
                case "`":
                    robot.keyPress(192);
                    robot.delay((int) t1);
                    robot.keyRelease(192);
                    robot.delay((int) t1);
                    break;
                case "-":
                    robot.keyPress(45);
                    robot.delay((int) t1);
                    robot.keyRelease(45);
                    robot.delay((int) t1);
                    break;
                case "=":
                    robot.keyPress(KeyEvent.VK_EQUALS);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_EQUALS);
                    robot.delay((int) t1);
                    break;
                case "[":
                    robot.keyPress(KeyEvent.VK_OPEN_BRACKET);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_OPEN_BRACKET);
                    robot.delay((int) t1);
                    break;
                case "]":
                    robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET);
                    robot.delay((int) t1);
                    break;
                case ";":
                    robot.keyPress(KeyEvent.VK_SEMICOLON);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SEMICOLON);
                    robot.delay((int) t1);
                    break;
                case "'":
                    robot.keyPress(KeyEvent.VK_QUOTE);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_QUOTE);
                    robot.delay((int) t1);
                    break;
                case ",":
                    robot.keyPress(44);
                    robot.delay((int) t1);
                    robot.keyRelease(44);
                    robot.delay((int) t1);
                    break;
                case ".":
                    robot.keyPress(46);
                    robot.delay((int) t1);
                    robot.keyRelease(46);
                    robot.delay((int) t1);
                    break;
                case "/":
                    robot.keyPress(47);
                    robot.delay((int) t1);
                    robot.keyRelease(47);
                    robot.delay((int) t1);
                    break;
                case "~":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(192);
                    robot.delay((int) t1);
                    robot.keyRelease(192);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "_":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(45);
                    robot.delay((int) t1);
                    robot.keyRelease(45);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "+":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_EQUALS);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_EQUALS);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "{":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_OPEN_BRACKET);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_OPEN_BRACKET);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "}":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case ":":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_SEMICOLON);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SEMICOLON);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "''":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(KeyEvent.VK_QUOTE);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_QUOTE);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "<":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(44);
                    robot.delay((int) t1);
                    robot.keyRelease(44);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case ">":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(46);
                    robot.delay((int) t1);
                    robot.keyRelease(46);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "?":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyPress(47);
                    robot.delay((int) t1);
                    robot.keyRelease(47);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
            }

            //特殊符号模块
            switch (sr) {
                case "Esc":
                    robot.keyPress(KeyEvent.VK_ESCAPE);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_ESCAPE);
                    robot.delay((int) t1);
                    break;
                case "+Esc":
                    robot.keyPress(KeyEvent.VK_ESCAPE);
                    robot.delay((int) t1);
                    break;
                case "-Esc":
                    robot.keyRelease(KeyEvent.VK_ESCAPE);
                    robot.delay((int) t1);
                    break;
                case "Tab":
                    robot.keyPress(KeyEvent.VK_TAB);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_TAB);
                    robot.delay((int) t1);
                    break;
                case "+Tab":
                    robot.keyPress(KeyEvent.VK_TAB);
                    robot.delay((int) t1);
                    break;
                case "-Tab":
                    robot.keyRelease(KeyEvent.VK_TAB);
                    robot.delay((int) t1);
                    break;
                case "Cap":
                    robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
                    robot.delay((int) t1);
                    break;
                case "+Cap":
                    robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                    robot.delay((int) t1);
                    break;
                case "-Cap":
                    robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
                    robot.delay((int) t1);
                    break;
                case "Shift":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "+Shift":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "-Shift":
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay((int) t1);
                    break;
                case "Ctrl":
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.delay((int) t1);
                    break;
                case "+Ctrl":
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.delay((int) t1);
                    break;
                case "-Ctrl":
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.delay((int) t1);
                    break;
                case "Win":
                    robot.keyPress(KeyEvent.VK_WINDOWS);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_WINDOWS);
                    robot.delay((int) t1);
                    break;
                case "+Win":
                    robot.keyPress(KeyEvent.VK_WINDOWS);
                    robot.delay((int) t1);
                    break;
                case "-Win":
                    robot.keyRelease(KeyEvent.VK_WINDOWS);
                    robot.delay((int) t1);
                    break;
                case "Alt":
                    robot.keyPress(KeyEvent.VK_ALT);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_ALT);
                    robot.delay((int) t1);
                    break;
                case "+Alt":
                    robot.keyPress(KeyEvent.VK_ALT);
                    robot.delay((int) t1);
                    break;
                case "-Alt":
                    robot.keyRelease(KeyEvent.VK_ALT);
                    robot.delay((int) t1);
                    break;
                case "Space":
                    robot.keyPress(KeyEvent.VK_SPACE);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_SPACE);
                    robot.delay((int) t1);
                    break;
                case "+Space":
                    robot.keyPress(KeyEvent.VK_SPACE);
                    robot.delay((int) t1);
                    break;
                case "-Space":
                    robot.keyRelease(KeyEvent.VK_SPACE);
                    robot.delay((int) t1);
                    break;
                case "Left":
                    robot.keyPress(KeyEvent.VK_LEFT);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_LEFT);
                    robot.delay((int) t1);
                    break;
                case "+Left":
                    robot.keyPress(KeyEvent.VK_LEFT);
                    robot.delay((int) t1);
                    break;
                case "-Left":
                    robot.keyRelease(KeyEvent.VK_LEFT);
                    robot.delay((int) t1);
                    break;
                case "Down":
                    robot.keyPress(KeyEvent.VK_DOWN);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_DOWN);
                    robot.delay((int) t1);
                    break;
                case "+Down":
                    robot.keyPress(KeyEvent.VK_DOWN);
                    robot.delay((int) t1);
                    break;
                case "-Down":
                    robot.keyRelease(KeyEvent.VK_DOWN);
                    robot.delay((int) t1);
                    break;
                case "Right":
                    robot.keyPress(KeyEvent.VK_RIGHT);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_RIGHT);
                    robot.delay((int) t1);
                    break;
                case "+Right":
                    robot.keyPress(KeyEvent.VK_RIGHT);
                    robot.delay((int) t1);
                    break;
                case "-Right":
                    robot.keyRelease(KeyEvent.VK_RIGHT);
                    robot.delay((int) t1);
                    break;
                case "Up":
                    robot.keyPress(KeyEvent.VK_UP);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_UP);
                    robot.delay((int) t1);
                    break;
                case "+Up":
                    robot.keyPress(KeyEvent.VK_UP);
                    robot.delay((int) t1);
                    break;
                case "-Up":
                    robot.keyRelease(KeyEvent.VK_UP);
                    robot.delay((int) t1);
                    break;
                case "Enter":
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    robot.delay((int) t1);
                    break;
                case "+Enter":
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.delay((int) t1);
                    break;
                case "-Enter":
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    robot.delay((int) t1);
                    break;
                case "Back":
                    robot.keyPress(KeyEvent.VK_BACK_SPACE);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_BACK_SPACE);
                    robot.delay((int) t1);
                    break;
                case "+Back":
                    robot.keyPress(KeyEvent.VK_BACK_SPACE);
                    robot.delay((int) t1);
                    break;
                case "-Back":
                    robot.keyRelease(KeyEvent.VK_BACK_SPACE);
                    robot.delay((int) t1);
                    break;
                case "Delete":
                    robot.keyPress(KeyEvent.VK_DELETE);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_DELETE);
                    robot.delay((int) t1);
                    break;
                case "+Delete":
                    robot.keyPress(KeyEvent.VK_DELETE);
                    robot.delay((int) t1);
                    break;
                case "-Delete":
                    robot.keyRelease(KeyEvent.VK_DELETE);
                    robot.delay((int) t1);
                    break;
                case "PgUp":
                    robot.keyPress(KeyEvent.VK_PAGE_UP);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_PAGE_UP);
                    robot.delay((int) t1);
                    break;
                case "+PgUp":
                    robot.keyPress(KeyEvent.VK_PAGE_UP);
                    robot.delay((int) t1);
                    break;
                case "-PgUp":
                    robot.keyRelease(KeyEvent.VK_PAGE_UP);
                    robot.delay((int) t1);
                    break;
                case "PgDn":
                    robot.keyPress(KeyEvent.VK_PAGE_DOWN);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
                    robot.delay((int) t1);
                    break;
                case "+PgDn":
                    robot.keyPress(KeyEvent.VK_PAGE_DOWN);
                    robot.delay((int) t1);
                    break;
                case "-PgDn":
                    robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
                    robot.delay((int) t1);
                    break;
                case "Ins":
                    robot.keyPress(KeyEvent.VK_INSERT);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_INSERT);
                    robot.delay((int) t1);
                    break;
                case "+Ins":
                    robot.keyPress(KeyEvent.VK_INSERT);
                    robot.delay((int) t1);
                    break;
                case "-Ins":
                    robot.keyRelease(KeyEvent.VK_INSERT);
                    robot.delay((int) t1);
                    break;
                case "Home":
                    robot.keyPress(KeyEvent.VK_HOME);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_HOME);
                    robot.delay((int) t1);
                    break;
                case "+Home":
                    robot.keyPress(KeyEvent.VK_HOME);
                    robot.delay((int) t1);
                    break;
                case "-Home":
                    robot.keyRelease(KeyEvent.VK_HOME);
                    robot.delay((int) t1);
                    break;
                case "End":
                    robot.keyPress(KeyEvent.VK_END);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_END);
                    robot.delay((int) t1);
                    break;
                case "+End":
                    robot.keyPress(KeyEvent.VK_END);
                    robot.delay((int) t1);
                    break;
                case "-End":
                    robot.keyRelease(KeyEvent.VK_END);
                    robot.delay((int) t1);
                    break;
            }

            //F数字模块
            switch (sr) {
                case "F1":
                    robot.keyPress(KeyEvent.VK_F1);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_F1);
                    robot.delay((int) t1);
                    break;
                case "F2":
                    robot.keyPress(KeyEvent.VK_F2);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_F2);
                    robot.delay((int) t1);
                    break;
                case "F3":
                    robot.keyPress(KeyEvent.VK_F3);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_F3);
                    robot.delay((int) t1);
                    break;
                case "F4":
                    robot.keyPress(KeyEvent.VK_F4);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_F4);
                    robot.delay((int) t1);
                    break;
                case "F5":
                    robot.keyPress(KeyEvent.VK_F5);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_F5);
                    robot.delay((int) t1);
                    break;
                case "F6":
                    robot.keyPress(KeyEvent.VK_F6);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_F6);
                    robot.delay((int) t1);
                    break;
                case "F7":
                    robot.keyPress(KeyEvent.VK_F7);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_F7);
                    robot.delay((int) t1);
                    break;
                case "F8":
                    robot.keyPress(KeyEvent.VK_F8);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_F8);
                    robot.delay((int) t1);
                    break;
                case "F9":
                    robot.keyPress(KeyEvent.VK_F9);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_F9);
                    robot.delay((int) t1);
                    break;
                case "F10":
                    robot.keyPress(KeyEvent.VK_F10);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_F10);
                    robot.delay((int) t1);
                    break;
                case "F11":
                    robot.keyPress(KeyEvent.VK_F11);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_F11);
                    robot.delay((int) t1);
                    break;
                case "F12":
                    robot.keyPress(KeyEvent.VK_F12);
                    robot.delay((int) t1);
                    robot.keyRelease(KeyEvent.VK_F12);
                    robot.delay((int) t1);
                    break;
            }
        }

    }
}