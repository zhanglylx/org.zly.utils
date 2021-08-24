package org.zly.utils.adb;

import org.zly.utils.DynamicSplicing.StringDynamicSplicingProxy;
import org.zly.utils.adb.shell.Input;
import org.zly.utils.adb.shell.Shell;

import java.util.HashMap;
import java.util.Map;


/**
 * 使用门面模式封装调用代理的步骤
 *
 * @author zly
 * @version 1.0
 * @date 2021/1/24 14:14
 */
public class AdbCodeFactory {
    //    使用threadLocal单例模式进行缓存
//    其实访问adb本身速度不会很快，其实这里增加的速度并不能为整体质的性能提升
    private static final ThreadLocal<Map<Class<? extends AdbCode>, ? super AdbCode>> threadLocal =
            new ThreadLocal<Map<Class<? extends AdbCode>, ? super AdbCode>>() {
                @Override
                protected Map<Class<? extends AdbCode>, ? super AdbCode> initialValue() {
                    return new HashMap<>();
                }
            };

    public static Input getInput() {
        return getInstance(new Input() {
        });
    }

    public static NoShell getNoShell() {
        return getInstance(new NoShell() {
        });
    }

    public static Shell getShell() {
        return getInstance(new Shell() {
        });
    }


    /**
     * @param obj
     * @param <T>
     * @return obj的代理实例
     */
    @SuppressWarnings("all")
    private static <T extends AdbCode> T getInstance(T obj) {
        Map<Class<? extends AdbCode>, ? super AdbCode> map = threadLocal.get();
        T value = (T) map.get(obj.getClass());
        if (value != null) {
            value.clear();
            return value;
        }
//        这里是想通过动态代理生成接口
//        T instance = (T) mapInstance.get(clazz);
//        if (instance == null) {
//            instance = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
//                    new InvocationHandler() {
//                        @Override
//                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                          这里无法使用method.invoke,因为无法传入实例，生成这个接口的实例是通过动态代理生成的
//                           但是调用method.invoke又需要一个实例，这就尴尬了，在需要返回值并且是实现父接口的相同方法
//                          直接使用java8 default 生成的时，想要获取返回内容，就必须是这个接口的实例，所以这里
//                          暂时放弃使用动态代理的方式生成类
//                            return null;
//                        }
//                    });
//            mapInstance.put(clazz, instance);
//        }
        T result = new StringDynamicSplicingProxy().getInstance(obj);
        map.put(obj.getClass(), result);
        return result;
    }
}
