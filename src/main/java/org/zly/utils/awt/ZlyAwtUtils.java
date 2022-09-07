package org.zly.utils.awt;

import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * @author zhanglianyu
 * @date 2022-08-14 19:35
 */
public class ZlyAwtUtils {

    /**
     * 将字符内容拷贝到剪贴板
     * @param content
     */
    public static void copyToClipboard(String content){
        Transferable canonicalNameSelection = new StringSelection(content);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(canonicalNameSelection, null);
    }
}
