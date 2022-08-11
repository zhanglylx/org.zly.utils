package org.zly.utils.adb;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.zly.utils.io.ZlyWindosUtils;

import java.util.List;
import java.util.Objects;

public class AdbUtils {
    /**
     * @param code 命令代码
     * @return
     * @notice 已经自动拼接首行命令:adb
     */
    @SneakyThrows
    public static List<String> execute(String... code) {
        List<String> list = ZlyWindosUtils.dosExecute(StringUtils.join(code, " "));
        list.removeIf(StringUtils::isBlank);
//        过滤掉最后为无内容情况
        if (list.size() > 0 && StringUtils.isBlank(list.get(list.size() - 1))) list.remove(list.size() - 1);
        return list;
    }

    public static List<String> execute(AdbCode adbCode) {
        Objects.requireNonNull(adbCode);
        return execute(adbCode.getResult());
    }

}