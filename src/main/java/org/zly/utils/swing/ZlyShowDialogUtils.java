package org.zly.utils.swing;

import javax.swing.*;
import java.util.List;

/**
 * 提示框
 *
 * @author zly
 * @version 1.0
 * @date 2021/2/9 14:48
 */
public class ZlyShowDialogUtils {
    static {
        System.setProperty("java.awt.headless", "false");
    }
    public static void showError(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "错误提示", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(String infoMessage) {
        JOptionPane.showMessageDialog(null, infoMessage, "消息提示", JOptionPane.INFORMATION_MESSAGE);

    }

    public static String showInput(String message) {
        return JOptionPane.showInputDialog(null, message, "请根据信息内容输入", JOptionPane.INFORMATION_MESSAGE);
    }


    public static String showInput(String message, String defaultValue) {
        String info = JOptionPane.showInputDialog(null, message, "请根据信息内容输入", JOptionPane.INFORMATION_MESSAGE);
        if (info == null) return defaultValue;
        return info;
    }

    public static boolean showYesOrNo(String text) {
        final boolean b = JOptionPane.showConfirmDialog(null, text, "请确认", JOptionPane.YES_NO_OPTION) == 0;//返回值为0或1
        return b;
    }

    public static String showListSelect(String message, List<?> items) {
        return showListSelect(message, items.toArray());
    }

    public static String showListSelect(String message, Object... list) {
        Object o = JOptionPane.showInputDialog(null,
                message + ":\n", "提示",
                JOptionPane.PLAIN_MESSAGE, null, list, list[0]);
        if (o == null) return null;
        return o.toString();
    }

}
