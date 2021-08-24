package org.zly.utils.date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class DateTimeUtils {

    /**
     * 按照指定的语法格式解析日期
     *
     * @param date    日期
     * @param pattern {@code date}的语法格式
     * @return 返回解析后的DateTime
     */
    public static DateTime parse(String date, String pattern) {
//        DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder();
        DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
        return DateTime.parse(date, format);
    }

    /**
     * 计算月有多少天
     *
     * @param dateTime 日期
     * @return 返回有多少天
     */
    public static int calculateMonthDay(DateTime dateTime) {
        return dateTime.dayOfMonth().getMaximumValue();
    }

    /**
     * 计算月有多少天
     *
     * @param year  哪一年
     * @param month 哪一月
     * @return 返回有多少天
     */
    public static int calculateMonthDay(int year, int month) {
        return calculateMonthDay(DateTime.parse(year + "-" + month));
    }

    /**
     * 默认以当前年为维度计算指定月份有多少天
     *
     * @param month 指定的月份
     * @return 返回有多少谈
     */
    public static int calculateMonthDay(int month) {
        return calculateMonthDay(DateTime.now().withMonthOfYear(10));
    }


    public static void main(String[] args) {
        int weekOfYear = 35;

        LocalDate firstDay = new LocalDate().withWeekOfWeekyear(weekOfYear).withDayOfWeek(1);
        LocalDate lastDay = new LocalDate().withWeekOfWeekyear(weekOfYear).withDayOfWeek(7);

        System.out.println("Week of Year " + weekOfYear + "; " + firstDay.toString("d MMM") + " - " + lastDay.toString("d MMM"));
    }

}
