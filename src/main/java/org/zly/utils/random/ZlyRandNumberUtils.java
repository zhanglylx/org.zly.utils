package org.zly.utils.random;

import org.apache.commons.lang3.RandomUtils;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.*;

public class ZlyRandNumberUtils {
    /**
     * 获取随机数,包前不包后
     *
     * @param min 最小值
     * @param max 最大值
     * @return 最小值到最大值减一的随机数
     */

    public static int nextInt(int min, int max) {
        if (min > max) throw new IllegalArgumentException("min > max: min=" + min + " max=" + max);
        return new SecureRandom().nextInt(max) % (max - min) + min;
    }

    public static long nextLong(long min, long max) {
        if (min > max) throw new IllegalArgumentException("min > max: min=" + min + " max=" + max);
        if (min < 0 && max < 0) {
            return -ZlyRandNumberUtils.nextLong(-min, -max);
        }
        if (min < 0) {
            if (RandomUtils.nextBoolean()) {
                return ~RandomUtils.nextLong(0, -min + 1);
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






}
