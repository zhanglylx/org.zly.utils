package org.zly.utils.network;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author zly
 * @version 1.0
 * @date 2021/8/14 22:23
 */
public abstract class AbstractBodyType<R> implements BodyType<R> {
    protected  Class<?> templateClass;

    public AbstractBodyType(Class<?> templateClass) {
        this.templateClass = templateClass;
    }

    public AbstractBodyType() {
    }

    public void setTemplateClass(Class<?> templateClass) {
        this.templateClass = templateClass;
    }

    @Override
    public Class<?> getTemplateClass() {
        return this.templateClass;
    }


}
