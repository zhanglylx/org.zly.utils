package org.zly.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;

public class NumberUtils {

    public static byte switchNegative(byte number) {
        return (byte) switchNegative(Long.valueOf(number));
    }

    public static short switchNegative(short number) {
        return (short) switchNegative(Long.valueOf(number));
    }

    public static int switchNegative(int number) {
        return (int) switchNegative(Long.valueOf(number));
    }

    public static long switchNegative(long number) {
        return (long) switchNegative(Double.valueOf(number));
    }

    public static float switchNegative(float number) {
        return (float) switchNegative(Double.valueOf(number));
    }

    public static double switchNegative(double number) {
        return number < 0D ? number : -number;
    }


}
