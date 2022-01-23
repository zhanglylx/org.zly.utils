package org.zly.utils;

import org.zly.utils.random.ZlyRandomNumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * double类型运算
 */
public class DoubleOperationUtils {
    private static final int DIV_SCALE = 10;//默认除法运算精度

    /**
     * 提供精确的加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 返回一个String的double,取消科学计数法
     *
     * @param d
     * @param scale
     * @return
     */
    public static String formatDouble(double d, int scale) {
        NumberFormat nf = NumberFormat.getInstance();
        //设置保留多少位小数
        nf.setMaximumFractionDigits(scale);
        // 取消科学计数法
        nf.setGroupingUsed(false);
        //返回结果
        return nf.format(d);
    }

    public static void main(String[] args) {
        System.out.println(ZlyRandomNumberUtils.nextDouble(0.1, Double.MAX_VALUE, 2));
        System.out.println(DoubleOperationUtils.formatDouble(ZlyRandomNumberUtils.nextDouble(0.1, Double.MAX_VALUE, 2), 20));
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供(相对)精确的除法运算
     * 当除不尽时，使用默认精度：DIV_SCALE
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DIV_SCALE);
    }

    /**
     * 提供(相对)精确的除法运算
     * 当除不尽时，使用参数scale指定精度，进行四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示精确到小数点后几位
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("scale不能小于0");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v1    需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double roundHalfUp(double v1, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("scale不能小于0");
        }
        BigDecimal b = new BigDecimal(Double.toString(v1));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 提供精确的小数位处理,不进行四舍五入
     *
     * @param v1    需要处理的数字
     * @param scale 小数点后保留几位
     * @return 保留几位后的数字
     */
    public static double roundDown(double v1, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("scale不能小于0");
        }
        BigDecimal b = new BigDecimal(Double.toString(v1));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, RoundingMode.DOWN).doubleValue();
    }


}
