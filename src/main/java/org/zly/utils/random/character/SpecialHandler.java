package org.zly.utils.random.character;

import org.zly.utils.random.RandomHandler;
import org.zly.utils.random.ZlyRandNumberUtils;

public class SpecialHandler implements RandomHandler<String> {

    private final static char[] CHARS = ("`~!@#$%^&*()_-+=/*,.<>][{}\\|/?;:'\"").toCharArray();

    @Override
    public String nextRandom() {
        return String.valueOf(CHARS[ZlyRandNumberUtils.nextInt(0, CHARS.length)]);
    }
}
