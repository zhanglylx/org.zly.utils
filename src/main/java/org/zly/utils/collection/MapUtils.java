package org.zly.utils.collection;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class MapUtils {

    /**
     * <p>检查maps中是否存在空<p/>
     * <pre>
     * MapUtils.isAnyBlank(null)    = true
     * MapUtils.isAnyBlank({"",""},null)  = true
     * MapUtils.isAnyBlank(Map[0])      = true
     * </pre>
     *
     * @param maps 检查的map
     * @return 只要有一个map是Null获取size等于0则返回true
     */
    public static boolean isAnyBlank(Map<?, ?>... maps) {
        if (maps == null || maps.length == 0) return true;
        for (Map<?, ?> entry : maps) {
            if (isBlank(entry)) return true;
        }
        return false;
    }

    /**
     * 检查map是否为空
     *
     * @param map
     * @return 为空获取size=0 返回true
     */
    public static boolean isBlank(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}
