package org.zly.utils.record;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PascalNameFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;
import org.springframework.http.HttpEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zly
 * @version 1.0
 * @date 2021/7/19 22:21
 */
public class FastJsonRecord {
    /*
    设置名字
    @JSONField(name = "3434343")
    private String Account;
     */

    /**
     * 首字母大写
     */
    public static void capitalize() {
        Map<String, Object> map = new HashMap<>();
        map.put("user", "ss");
        System.out.println(
                JSONObject.toJSONString(map, new PascalNameFilter())
        );
//        全局设置,针对javaBean
//        TypeUtils.compatibleWithFieldName = true;
//        System.out.println(
//                JSONObject.toJSONString(map)
//        );
    }

    /**
     * 保留nullkey和值
     */
    public static void saveNullValue(){
        Map<String, Object> map = new HashMap<>();
        map.put("user", null);
        System.out.println(JSONObject.toJSONString(map, SerializerFeature.WriteMapNullValue));
    }

    public static void main(String[] args) {
        saveNullValue();
    }
}
