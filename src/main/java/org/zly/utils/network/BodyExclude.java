package org.zly.utils.network;

import java.lang.annotation.*;

/**
 * @author zly
 * @version 1.0
 * @date 2021/8/9 20:36
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface BodyExclude {
}
