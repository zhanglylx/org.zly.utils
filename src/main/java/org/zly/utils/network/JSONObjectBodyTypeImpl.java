package org.zly.utils.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author zly
 * @version 1.0
 * @date 2021/8/11 10:15
 */
public class JSONObjectBodyTypeImpl extends AbstractBodyType<JSONObject> {

    public JSONObjectBodyTypeImpl(Class<?> templateClass) {
        super(templateClass);
    }

    @Override
    public JSONObject getHandler(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(map);
        return jsonObject;
    }
}
