package org.zly.utils.random.type;

import org.zly.utils.random.ZlyRandom;
import org.zly.utils.random.ZlyRandomNumberUtils;

public class ZlyRandomIpAddress implements ZlyRandom<String> {
    @Override
    public String nextRandom() {
        return
                ZlyRandomNumberUtils.nextInt(10, 1000)
                        + "." + ZlyRandomNumberUtils.nextInt(1, 1000)
                        + "." + ZlyRandomNumberUtils.nextInt(1, 1000)
                        + "." + ZlyRandomNumberUtils.nextInt(1, 1000)
                ;
    }
}
