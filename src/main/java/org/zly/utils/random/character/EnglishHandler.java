package org.zly.utils.random.character;

import org.zly.utils.random.RandomHandler;
import org.zly.utils.random.ZlyRandNumberUtils;

public class EnglishHandler implements RandomHandler<String> {

    @Override
    public String nextRandom() {
        String str;
        str = (char) (Math.random() * 26 + 'A') + "";
        if (ZlyRandNumberUtils.nextInt(0, 2) == 0) str = str.toLowerCase();
        return str;
    }
}
