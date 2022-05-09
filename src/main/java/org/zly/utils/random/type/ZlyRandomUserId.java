package org.zly.utils.random.type;

import lombok.SneakyThrows;
import org.zly.utils.random.ZlyRandom;
import org.zly.utils.random.ZlyRandomNumberUtils;
import org.zly.utils.random.ZlyRandomSetUtils;

import java.util.ArrayList;
import java.util.List;

public class ZlyRandomUserId implements ZlyRandom<String> {

    private static long time = 0;

    @SneakyThrows
    public synchronized static String getUserId() {
        long t = System.nanoTime();
        if (time == 0) {
            time = t;
            return String.valueOf(t);
        }
        if (t == time) Thread.sleep(1);
        t = System.nanoTime();
        time = t;
        return String.valueOf(t);
    }

    @Override
    public String nextRandom() {
        return getUserId();
    }

}
