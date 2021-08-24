package org.zly.utils.network;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.zly.utils.ZlyReflectUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author zly
 * @version 1.0
 * @date 2021/8/11 13:48
 */
public abstract class ObjectBodyTypeImpl<R> extends AbstractBodyType<R> {

    public ObjectBodyTypeImpl() {
    }

    public ObjectBodyTypeImpl(Class<?> templateClass) {
        super(templateClass);
    }

    @Override
    public R getHandler(Map<String, Object> map) {
        return new JSONObjectBodyTypeImpl(this.templateClass).getHandler(map).toJavaObject((Class<R>) (ZlyReflectUtils.getGenericClass(this.getClass())));
    }


}
