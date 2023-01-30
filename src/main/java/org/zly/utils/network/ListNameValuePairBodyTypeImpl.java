package org.zly.utils.network;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author zly
 * @version 1.0
 * @date 2021/8/16 19:43
 */
public class ListNameValuePairBodyTypeImpl extends AbstractBodyType<List<NameValuePair>> {

    private boolean nullFilling = false;

    private String nullFillingValue = null;

    public ListNameValuePairBodyTypeImpl(Class<?> templateClass, boolean nullFilling) {
        this(templateClass, nullFilling, null);
    }

    public ListNameValuePairBodyTypeImpl(Class<?> templateClass, boolean nullFilling, String nullFillingValue) {
        this(templateClass);
        this.nullFilling = nullFilling;
        this.nullFillingValue = nullFillingValue;
    }

    public ListNameValuePairBodyTypeImpl(Class<?> templateClass) {
        super(templateClass);
    }


    @Override
    public List<NameValuePair> getHandler(Map<String, Object> map) {
        List<NameValuePair> list = new ArrayList<>();
        map.forEach(new BiConsumer<String, Object>() {
            @Override
            public void accept(String s, Object o) {
                if (o != null) {
                    list.add(new BasicNameValuePair(s, o.toString()));
                } else {
                    if (nullFilling) list.add(new BasicNameValuePair(s, nullFillingValue));
                }
            }
        });
        return list;
    }

    public boolean isNullFilling() {
        return nullFilling;
    }

    public void setNullFilling(boolean nullFilling) {
        this.nullFilling = nullFilling;
    }

    public String getNullFillingValue() {
        return nullFillingValue;
    }

    public void setNullFillingValue(String nullFillingValue) {
        this.nullFillingValue = nullFillingValue;
    }
}
