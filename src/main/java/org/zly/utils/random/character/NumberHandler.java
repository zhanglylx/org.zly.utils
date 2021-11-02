package org.zly.utils.random.character;

import org.zly.utils.random.RandomHandler;
import org.zly.utils.random.ZlyRandNumberUtils;

public class NumberHandler implements RandomHandler<String> {

    @Override
    public String nextRandom() {
        return String.valueOf(ZlyRandNumberUtils.nextInt(10));
    }
}
