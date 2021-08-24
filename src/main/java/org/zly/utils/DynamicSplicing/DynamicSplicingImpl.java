package org.zly.utils.DynamicSplicing;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zly
 * @version 1.0
 * @date 2021/1/26 17:19
 */
@SuppressWarnings("all")
public abstract class DynamicSplicingImpl<V> implements DynamicSplicing<V>, InvocationHandler {
    protected DynamicSplicing<V> dynamicSplicing;
    protected final static List<String> ROOT_IGNORE_METHODS
            = new ArrayList<>();

    static {
        for (Method method : DynamicSplicing.class.getMethods()) {
            ROOT_IGNORE_METHODS.add(method.getName());
        }

        for (Method method : Object.class.getMethods()) {
            ROOT_IGNORE_METHODS.add(method.getName());
        }
    }

    public <T extends DynamicSplicing<V>> T getInstance(T dynamicSplicing) {
        this.dynamicSplicing = dynamicSplicing;
        T t = (T) Proxy.newProxyInstance(
                this.dynamicSplicing.getClass().getClassLoader(),
                this.dynamicSplicing.getClass().getInterfaces(),
                this
        );
        join(this.dynamicSplicing.init());
        return t;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (ROOT_IGNORE_METHODS.contains(method.getName())) {
            try {
                return method.invoke(this, args);
            } catch (IllegalArgumentException e) {
                return method.invoke(this.dynamicSplicing, args);
            }
        }
        if (!this.dynamicSplicing.ignoreGlobalMethonName()
                &&
                (this.dynamicSplicing.ignoreMethonName() == null
                        || !this.dynamicSplicing.ignoreMethonName().contains(method.getName()))
        ) {
            join(method.getName());
        }
//        这种可变长的参数在经过代理后，直接是new Object[]{args},所以需要进行处理
        if (args != null && args[0] instanceof Object[]) {
            args = (Object[]) args[0];
        }
        Object o = method.invoke(this.dynamicSplicing, args);
//        这里是处理某个参数需要子类自己拼接，所以当返回值不是void时，交由子类自己处理
        if (method.getReturnType() != Void.TYPE) {
            join(o);
        } else {
            join(args);
        }
        return o;
    }
}
