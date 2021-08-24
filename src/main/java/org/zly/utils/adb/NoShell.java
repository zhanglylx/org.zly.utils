package org.zly.utils.adb;

import java.io.File;

/**
 * @author zly
 * @version 1.0
 * @date 2021/1/25 11:15
 */
public interface NoShell extends AdbCode {
    @Override
    default String init() {
        return "adb";
    }

    default String install(String filePath) {
        return "-r " + filePath;
    }

    default String install(File filePath) {
        return install(filePath.toString());
    }

    default String pull(String src, String tar) {
        return src + " " + tar;
    }

    default String devices() {
        return "-l";
    }

}
