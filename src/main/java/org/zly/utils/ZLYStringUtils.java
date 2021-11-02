package org.zly.utils;

import org.apache.commons.lang3.StringUtils;
import org.zly.utils.random.ZlyRandNumberUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZLYStringUtils {


    /**
     * 检查字符串匹配度
     *
     * @param str1
     * @param str2
     * @return 未匹配成功数量
     */
    private static int ldStr(String str1, String str2) {
        int[][] d;    //矩阵
        int n = str1.length();
        int m = str2.length();
        int i;    //遍历str1的
        int j;    //遍历str2的
        char ch1;    //str1的
        char ch2;    //str2的
        int temp;    //记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) {    //初始化第一列
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) {    //初始化第一行
            d[0][j] = j;
        }
        for (i = 1; i <= n; i++) {    //遍历str1
            ch1 = str1.charAt(i - 1);
            //去匹配str2
            for (j = 1; j <= m; j++) {
                ch2 = str2.charAt(j - 1);
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                //左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }

    /**
     * 字符串的相似度
     *
     * @param matching 匹配字符串
     * @param target   目标字符串
     * @param scale    保留到几位小数
     * @return 百分比
     */
    public static double sim(String matching, String target, int scale) {
        if (matching == null) throw new IllegalArgumentException("matching为空");
        if (target == null) throw new IllegalArgumentException("target为空");
        int ld = ldStr(strHashAscSort(matching), strHashAscSort(target));
        double d = 1 - (double) ld / Math.max(matching.length(), target.length());
        return DoubleOperationUtils.roundHalfUp(d * 100, scale);

    }

    /**
     * 字符串的相似度
     *
     * @param matching 匹配字符串
     * @param target   目标字符串
     * @return
     */
    public static double sim(String matching, String target) {
        return sim(matching, target, 1);
    }


    public static String getNoAssignVersion(String assignVersion) {
        return getNoAssignVersion(assignVersion, false);
    }

    /**
     * 获取不是指定的版本号
     * 比当前版本号高
     *
     * @param assignVersion
     * @param currentSmall  比当前小
     * @return
     */
    public static String getNoAssignVersion(String assignVersion, boolean currentSmall) {
        if (StringUtils.isBlank(assignVersion)) throw new NullPointerException();
        assignVersion = assignVersion.trim();
        if (!assignVersion.matches("(\\d+\\.){2}\\d+")) throw new IllegalArgumentException("参数格式不正确：" + assignVersion);
        int firstIndex = assignVersion.indexOf(".");
        int firstNumber = Integer.parseInt(assignVersion.substring(0, firstIndex));
        int n1;
        int n2 = ZlyRandNumberUtils.nextInt(0, 30);
        int n3 = ZlyRandNumberUtils.nextInt(0, 30);
        if (currentSmall) {
            if (firstNumber < 2) {
                n1 = firstNumber;
                n2 = Integer.parseInt(assignVersion.substring(firstIndex + 1, assignVersion.indexOf(".", firstIndex + 1)));
                if (n2 < 1) {
                    int lastIndex = assignVersion.lastIndexOf(".");
                    n3 = Integer.parseInt(assignVersion.substring(lastIndex + 1));
                    if (n3 < 1) {
                        throw new IllegalArgumentException("不能再小的版本:" + assignVersion);
                    } else {
                        n3 = ZlyRandNumberUtils.nextInt(0, n3);
                    }
                } else {
                    n2 = ZlyRandNumberUtils.nextInt(0, n2);
                }
            } else {
                n1 = ZlyRandNumberUtils.nextInt(1, firstNumber);
            }
        } else {
            n1 = ZlyRandNumberUtils.nextInt(firstNumber + ZlyRandNumberUtils.nextInt(5, 10), firstNumber + ZlyRandNumberUtils.nextInt(10, 20));
        }

        return n1
                + "."
                + n2
                + "."
                + n3;
    }

    public static void main(String[] args) {
        System.out.println(getNoAssignVersion("0.0.1", true));
    }

    /**
     * 替换换行符
     *
     * @param str       替换字符串
     * @param alternate 替换内容
     * @return
     */
    public static String replaceLineBreak(String str, String alternate) {
        Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
        Matcher m = CRLF.matcher(str);
        if (m.find()) {
            str = m.replaceAll(alternate);
        }
        return str;
    }

    /**
     * 编辑距离算法
     * 编辑距离的两字符串相似度
     *
     * @author jianpo.mo
     */

    private static int min(int one, int two, int three) {
        int min = one;
        if (two < min) {
            min = two;
        }
        if (three < min) {
            min = three;
        }
        return min;
    }


    /**
     * 字符串通过哈希升序排序
     *
     * @param str
     * @return
     */
    public static String strHashAscSort(String str) {
        if (str.length() <= 1) return str;       //如果只有一个元素就不用排序了
        char[] arr = str.toCharArray();
        boolean flag;
        for (int i = 0; i < str.length(); ++i) {
            // 提前退出冒泡循环的标志位,即一次比较中没有交换任何元素，这个数组就已经是有序的了
            flag = false;
            for (int j = 0; j < str.length() - i - 1; ++j) {        //此处你可能会疑问的j<n-i-1，因为冒泡是把每轮循环中较大的数飘到后面，
                // 数组下标又是从0开始的，i下标后面已经排序的个数就得多减1，总结就是i增多少，j的循环位置减多少
                if (arr[j] > arr[j + 1]) {        //即这两个相邻的数是逆序的，交换
                    char temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = true;
                }
            }

            if (!flag) break;//没有数据交换，数组已经有序，退出排序

        }
        return String.valueOf(arr);
    }


    /**
     * @param preNumber 前缀
     * @param index     序列
     * @return
     */
    public static String getUserNo(int preNumber, Long index) {
        //  （1）第一个%d代表整数类型（十进制），是preNumber的占位符，拼接字符串的时候会用preNumber的值进行替换。
        //  （2）第二个%010d，前面第一个0代表：数字前面补0；后面的10代表字符总长度为10，d代表整数类型。这个表达式的整体含义就是，用index的值来替换此处表达式，如果index的长度不足10位，则在index的前面用0补齐。
        return String.format("%d%010d", preNumber, index);
    }

}