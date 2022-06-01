package org.zly.utils.random.type;

import org.zly.utils.random.ZlyRandom;
import org.zly.utils.random.ZlyRandomNumberUtils;
import org.zly.utils.random.ZlyRandomSetUtils;

import java.util.ArrayList;
import java.util.List;

public class ZlyRandomMobilePhone implements ZlyRandom<String> {
    @Override
    public String nextRandom() {
        return ZlyRandomSetUtils.nextValue(PHONE_PREFIX) + ZlyRandomNumberUtils.nextLongLenth(8);
    }

    public static void main(String[] args) {
        System.out.println(new ZlyRandomMobilePhone().nextRandom());
    }
    private static final List<String> PHONE_PREFIX = new ArrayList<>();

    static {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                stringBuilder.append(1);
                stringBuilder.append(i);
                stringBuilder.append(j);
                PHONE_PREFIX.add(stringBuilder.toString());
                stringBuilder.delete(0,3);
            }
        }
    }

    public static List<String> getAllPhonePrefix() {
        return PHONE_PREFIX;
    }
}
