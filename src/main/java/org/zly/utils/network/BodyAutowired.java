package org.zly.utils.network;


import java.lang.annotation.*;


/**
 * @author zly
 * @version 1.0
 * @date 2021/8/9 20:15
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface BodyAutowired {

    String[] excludeParameters() default {};
}
