package org.zly.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanglianyu
 * @date 2023-06-13 14:35
 */
public class ZlyYmalUtils {

    public static Map<String, Object> yamlReplaceHandler(Map<String, Object> configMap, String key, String value) {
        return yamlReplaceHandler(configMap, key, value, "\\.");
    }

    /**
     * ymal文件内容替换或增加
     *
     * @param configMap         属性集合
     * @param key               属性key   格式:xxx.xxx.xxx
     * @param value             属性值
     * @param keySeparatorRegex key的分隔符，用于定义层级
     * @return 在configMap集合中替换或增加后的对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> yamlReplaceHandler(Map<String, Object> configMap, String key, String value, String keySeparatorRegex) {
        final String[] split = key.split(keySeparatorRegex);
        if (split.length == 1) {
            configMap.put(key, value);
        } else {
            Map<String, Object> element = configMap;
            Node latestElement = new Node();
            List<Node> nullList = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                if (element != null) {
                    if (i == split.length - 1) {
                        element.put(split[i], value);
                    } else {
                        try {
                            element = (Map<String, Object>) element.get(split[i]);
                        } catch (ClassCastException ignore) {
                            element = null;
                        }
                    }
                    if (element != null) {
                        latestElement.setE(element);
                        latestElement.setKey(split[i]);
                    }
                }
                if (element == null) {
                    Map<String, Object> e = new HashMap<>();
                    e.put(split[i], null);
                    Node node = new Node(e, split[i]);
                    nullList.add(node);
                    if (i == split.length - 1) {
                        e.put(split[i], value);
                        //                            将上一个节点的值关联到当前节点
                        for (int i1 = nullList.size() - 1; i1 > 0; i1--) {
                            final Node previousNode = nullList.get(i1 - 1);
                            final Node currentNode = nullList.get(i1);
                            previousNode.getE().put(previousNode.getKey(), currentNode.getE());
                        }
                        //                        如果latestElement中的e不为空，则代表寻找的key是在中途断了，所有需要再中途进行拼接
                        Map<String, Object> m = latestElement.getE() != null ? latestElement.getE() : configMap;
                        m.putAll(nullList.get(0).getE());
                    }
                }
            }
        }
        return configMap;
    }

    @Data
    public static class Node {
        private Map<String, Object> e;
        private String key;

        public Node() {
        }

        public Node(Map<String, Object> e, String key) {
            this.e = e;
            this.key = key;
        }
    }
}
