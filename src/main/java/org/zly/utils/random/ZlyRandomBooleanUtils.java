package org.zly.utils.random;

import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.zly.utils.date.ZlyDateUtils;

import java.util.Date;
import java.util.Objects;

public class ZlyRandomBooleanUtils {

    public static boolean next() {
        return RandomUtils.nextBoolean();
    }

}
