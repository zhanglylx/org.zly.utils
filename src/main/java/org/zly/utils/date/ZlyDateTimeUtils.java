package org.zly.utils.date;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;


public class ZlyDateTimeUtils {

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
     * 指定日期年
     * @param year  年份
     * @return 返回指定年份的1月1日 零时
     */
    public static DateTime newInterface(int year){
        return new DateTime(year,1,1,0,0);
    }

    /**
     * 指定日期年，月
     * @param year  年份
     * @param monthOfYear  月份
     * @return 返回指定年份和月的1日 零时
     */
    public static DateTime newInterface(int year,int monthOfYear){
        return new DateTime(year,monthOfYear,1,0,0);
    }

    /**
     * 指定年月日
     * @param year 年
     * @param monthOfYear  月
     * @param dayOfMonth   日
     * @return 指定年月日的 零时
     */
    public static DateTime newInterface(int year,int monthOfYear,int dayOfMonth){
        return new DateTime(year,monthOfYear,dayOfMonth,0,0);
    }

    /**
     * 指定当前系统年的月和日
     * @param monthOfCurrentYear  系统年下的月份
     * @param dayOfMonth          月份下的日
     * @return  返回当前系统年的指定月和日
     */
    public static DateTime newInterfaceOfCurrentYear(int monthOfCurrentYear,int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        return newInterface(calendar.get(Calendar.YEAR),monthOfCurrentYear,dayOfMonth);
    }

    /**
     *  指定当前年和当前月下的指定日期
     * @param dayOfMonth 指定年月下的天
     * @return 返回指定当前年和当前月下的指定日
     */
    public static DateTime newInterfaceOfCurrentYearOfMonth(int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        return newInterface(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),dayOfMonth);
    }


    /**
     * 计算年有多少天
     *
     * @param dateTime
     * @return
     */
    public static int calculateYearDay(DateTime dateTime) {
        return dateTime.dayOfYear().getMaximumValue();
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


        Calendar calendar = Calendar.getInstance();

        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH));
    }

}
