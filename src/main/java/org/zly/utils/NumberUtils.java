package org.zly.utils;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.client.utils.URIBuilder;
import org.zly.utils.io.ZlyExcelUtils;
import org.zly.utils.io.ZlyFileUtils;
import org.zly.utils.network.ZlyHttpUtils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static void main(String[] args) throws IOException {
        final Map<Integer, Map<String, String>> excelXlsx = ZlyExcelUtils.getExcelXlsx(new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\订单\\每班结帐报告 -- 收入总结.xlsx")), 1);
        Map<Integer, Map<String, String>> map = new LinkedHashMap<>();
        final int[] index = {0};
        excelXlsx.forEach(new BiConsumer<Integer, Map<String, String>>() {
            @Override
            public void accept(Integer integer, Map<String, String> stringStringMap) {
                final String count = stringStringMap.get("总套餐数");
                System.out.println(count);
                Double n = Double.valueOf(count);
                if(n>1){
                    BigDecimal b = new BigDecimal(stringStringMap.get("应收价值"));
                    final BigDecimal divide = b.divide(new BigDecimal(n), 2, RoundingMode.DOWN);
                    if(stringStringMap.get("套餐名称").equals("美团48元=50币 ")){
                        System.out.println(n);
                        System.out.println(b);
                        System.out.println("-===========");
                    }
                    for (int i = 0; i <n ; i++) {
                        final LinkedHashMap<String, String> objectObjectLinkedHashMap = new LinkedHashMap<>(stringStringMap);
                        objectObjectLinkedHashMap.put("应收价值",divide.toString());
                        objectObjectLinkedHashMap.put("总套餐数","1");
                        map.put(index[0]++,objectObjectLinkedHashMap);
                    }
                }else {
                    map.put(index[0]++,stringStringMap);
                }
            }
        });
        ZlyExcelUtils.createExcelFile(new File("C:\\Users\\Administrator\\Desktop\\订单\\每班结帐报告 -- 收入总结-1.xlsx"),"1",map);
        System.out.println(excelXlsx);
    }

}
