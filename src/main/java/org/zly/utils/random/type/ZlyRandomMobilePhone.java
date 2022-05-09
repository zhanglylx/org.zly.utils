package org.zly.utils.random.type;

import org.zly.utils.random.ZlyRandom;
import org.zly.utils.random.ZlyRandomNumberUtils;
import org.zly.utils.random.ZlyRandomSetUtils;

import java.util.ArrayList;
import java.util.List;

public class ZlyRandomMobilePhone implements ZlyRandom<String> {
    @Override
    public String nextRandom() {
        return "1" + ZlyRandomSetUtils.nextValue(new Integer[]{3, 5, 6, 7, 8, 9}) + ZlyRandomNumberUtils.nextLongLenth(9);
    }

    private static final List<String> PHONE_PREFIX = new ArrayList<>();

    static {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                PHONE_PREFIX.add(1 + i + j + "");
            }
        }
    }

    public static List<String> getAllPhonePrefix() {
        return PHONE_PREFIX;
    }
}
