package org.zly.utils.random;

import java.util.function.Consumer;

/**
 * @author zhanglianyu
 * @date 2022-08-15 16:24
 */
public class ZlyRandomFunction {
    public static void randomFunctionHandler(RandomFunction... randomFunction) {
        if (randomFunction == null || randomFunction.length == 0)
            throw new NullPointerException("randomFunction is null");
        final int i = ZlyRandomSetUtils.nextIndex(randomFunction);
        randomFunction[i].handler();
    }
}
