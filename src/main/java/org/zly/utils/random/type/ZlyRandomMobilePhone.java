package org.zly.utils.random.type;

import org.zly.utils.random.ZlyRandom;
import org.zly.utils.random.ZlyRandomNumberUtils;
import org.zly.utils.random.ZlyRandomSetUtils;

public class ZlyRandomMobilePhone implements ZlyRandom<String> {
    @Override
    public String nextRandom() {
        return "1" + ZlyRandomSetUtils.nextValue(new Integer[]{3, 5, 6, 7, 8, 9}) + ZlyRandomNumberUtils.nextLongLenth(9);
    }
}
