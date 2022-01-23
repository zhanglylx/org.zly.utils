package org.zly.utils.random;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.*;

public class ZlyRandomNumberUtils {
    /**
     * 获取随机数,包前不包后
     *
     * @param min 最小值
     * @param max 最大值
     * @return 最小值到最大值减一的随机数
     */

    public static int nextInt(int min, int max) {
        if (min > max) throw new IllegalArgumentException("min > max: min=" + min + " max=" + max);
//        return new SecureRandom().nextInt(max) % (max - min) + min;
        return (int) nextLong(min, max);
    }

    public static long nextLong(long min, long max) {
        if (min > max) throw new IllegalArgumentException("min > max: min=" + min + " max=" + max);
        if (min == max) return min;
        if (min < 0 && max < 0) {
            return -ZlyRandomNumberUtils.nextLong(-max, -min) - 1;
        }
        if (min < 0) {
            int startNumber = max == 0 ? 1 : 0;
            if (RandomUtils.nextBoolean() || max == 0) {
                return -RandomUtils.nextLong(startNumber, -min + 1);
            } else {
                return RandomUtils.nextLong(0, max);
            }
        }
        return RandomUtils.nextLong(min, max);
    }


    public static int nextInt(int max) {
        return nextInt(0, max);
    }

    /**
     * 获取1-9的随机数
     *
     * @return
     */
    public static int nextInt() {
        return nextInt(0, Integer.MAX_VALUE);
    }

    /**
     * 获取随机数的list
     *
     * @param number 需要多少个
     * @param min    参照最小值
     * @param max    参照最大值，不包含max
     * @return
     */
    public static List<Integer> nextIntList(int number, int min, int max) {
        if ((max - min) < number) throw new IllegalArgumentException("给定的参数数量小于获取数量");
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        final int pool = 30;
        CountDownLatch latch = new CountDownLatch(1);
        ThreadPoolExecutor threadPoolExecutor = new RandomThreadPool(pool, latch);
        int step = 10000;
        int finalMin = min;
        int finalMax = finalMin + step;
        while (finalMin != max) {
            if (finalMax > max) finalMax = max;
            threadPoolExecutor.execute(new RandomNumberList(finalMin, finalMax, list));
            finalMin = finalMax;
            finalMax = finalMin + step;
        }
        threadPoolExecutor.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Collections.shuffle(list);
        List<Integer> list1 = new ArrayList<>(list.subList(0, number));
        Collections.sort(list1);
        return list1;
    }

    private static class RandomThreadPool extends ThreadPoolExecutor {
        private final CountDownLatch countDownLatch;

        public RandomThreadPool(int nThreads, CountDownLatch countDownLatch) {
            super(nThreads, nThreads,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());
            this.countDownLatch = countDownLatch;
        }

        @Override
        protected void terminated() {
            countDownLatch.countDown();
        }
    }

    private static class RandomNumberList implements Runnable {
        private final int min;
        private final int max;
        private final List<Integer> list;

        public RandomNumberList(int min, int max, List<Integer> list) {
            this.min = min;
            this.max = max;
            this.list = list;
        }

        @Override
        public void run() {
            for (int i = min; i < max; i++) {
                this.list.add(i);
            }
        }
    }

    public static List<Integer> nextIntList(int number, Collection<?> max) {
        return nextIntList(number, 0, max.size());
    }

    public static List<Integer> nextIntList(int number, int max) {
        return nextIntList(number, 0, max);
    }

    /**
     * 获取指定位数的随机串
     *
     * @param lenth
     * @return
     */
    public static long nextLongLenth(int lenth) {
        if (lenth < 1) throw new IllegalArgumentException("lenth 小于0，lenth :" + lenth);
        if (lenth > 19) throw new IllegalArgumentException("lenth 大于19位数，lenth :" + lenth);
        if (lenth == 1) return nextInt(0, 10);
        return (long) ((Math.random() * 9 + 1) * Math.pow(10, lenth - 1));
    }


    public static double nextDouble(double minPlaceValue, double maxPlaceValue, int scale) {
        if (minPlaceValue > maxPlaceValue)
            throw new IllegalArgumentException("min大于max:" + minPlaceValue + " - " + maxPlaceValue);
        if (minPlaceValue == maxPlaceValue) return minPlaceValue;
        BigDecimal randomDrandomBig;
        if (minPlaceValue >= 0) {
            randomDrandomBig = BigDecimal.valueOf(RandomUtils.nextDouble(minPlaceValue, maxPlaceValue));
        } else if (maxPlaceValue < 0) {
            randomDrandomBig = BigDecimal.valueOf(-RandomUtils.nextDouble(-maxPlaceValue, -minPlaceValue));
            if (randomDrandomBig.doubleValue() == maxPlaceValue) return minPlaceValue;
        } else {
            if (RandomUtils.nextBoolean()) {
                randomDrandomBig = BigDecimal.valueOf(-RandomUtils.nextDouble(0, -minPlaceValue));
            } else {
                randomDrandomBig = BigDecimal.valueOf(RandomUtils.nextDouble(0, maxPlaceValue));
            }
        }
        randomDrandomBig = randomDrandomBig.setScale(scale, RoundingMode.DOWN);
        return randomDrandomBig.doubleValue();
    }

    public static boolean isDoubleMantissaNotZero(BigDecimal bigDecimalValue, int scale) {
        return !isDoubleMantissaIsZero(bigDecimalValue, scale);
    }

    public static boolean isDoubleMantissaIsZero(BigDecimal bigDecimalValue, int scale) {
        if (scale == 0) return false;
        String number = bigDecimalValue.toString();
        return number.endsWith("0");
    }

    /**
     * @param minPlaceValue  整数位最小数
     * @param maxPlaceValue  整数位最大值-1
     * @param scale          需要几位小数
     * @param mantissaIsZero 尾数是否允许0，允许的话，随机出来的值的位数会小于指定位数
     * @return 整数位+小数位随机后的值
     */
    public static BigDecimal nextBigDecimal(long minPlaceValue, long maxPlaceValue, int scale, boolean mantissaIsZero) {
        if (scale < 0) throw new IllegalArgumentException("scale不能小于0,实际为:" + scale);
        int index = 0;
        while (true) {
            double random = scale == 0 ? 0 : Math.random();
            BigDecimal randomBig = BigDecimal.valueOf(random).setScale(scale, RoundingMode.DOWN);
            int cardinality = 16;//基数，Math.random()返回的小数点长度
//            大于16位代表需要增长小数位
//            计算方法，如:  10/0.1=0.01，按照此列计算，
//            Math.pow(10, cardinality * i) 可以计算出 random每一次需要相加的数据
//            如: random = 0.2
//            cardinality = 1;
//            scale =2 此时，需要将random增长1位才能满足需求,通过   int numberIndex = scale / cardinality + 1;  2/1+1 = 2  加1的目的是多一次循环，因为除数后转int没有进行四舍五入，防止误差出现
//            第一次循环， Math.pow(10, 1 * 1) = 10;    0.2/10 = 0.02;   randomBig= 0.02 + 0.2 = 0.22;
//            第二次循环  Math.pow(10, 1 * 2) = 100    0.2/100 = 0.002  randomBig= 0.22 + 0.002 = 0.222
//            这样相加可以保证在计算后小数位的前几位出现大量0的情况
            if (scale > cardinality) {
                int numberIndex = scale / cardinality + 1;
                BigDecimal divide;
                for (int i = 1; i <= numberIndex; i++) {
                    divide = BigDecimal.valueOf(random).divide(BigDecimal.valueOf(Math.pow(10, cardinality * i)), scale, RoundingMode.HALF_UP);
                    randomBig = randomBig.add(divide);
                }
            }
            randomBig = randomBig.add(BigDecimal.valueOf(ZlyRandomNumberUtils.nextLong(minPlaceValue, maxPlaceValue)));
            if (!mantissaIsZero) {
                if (isDoubleMantissaNotZero(randomBig, scale)) return randomBig;
            } else {
                return randomBig;
            }
//            快速失败机制，防止系统bug造成死循环,出现bug后联系作者修复
            if (index++ > 100)
                throw new RuntimeException("系统出现bug,请联系作者:" + StringUtils.join(new Object[]{minPlaceValue, maxPlaceValue, scale, mantissaIsZero}, ","));
        }
    }

}
