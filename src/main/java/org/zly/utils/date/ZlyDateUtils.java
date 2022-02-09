package org.zly.utils.date;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ZlyDateUtils {
    public final static String GENERAL_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String YMDHMSS = "yyyyMMddHHmmssSS";
    public final static String YMDHMS = "yyyyMMddHHmmss";
    public final static String YYMM = "yyyyMM";
    public final static String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 获取时间
     *
     * @param format 指定的格式
     * @return
     */
    public static String getStrDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }


    public static Date plusDays(Date standardDate, int day) {
        Objects.requireNonNull(standardDate);
        return new DateTime(standardDate).plusDays(day).toDate();
    }


    public static Date getDateFormat(String time, String format) {
        return getDateConversion(time, format);
    }

    /**
     * 获取系统时间，默认时间格式:yyyy-MM-dd HH:mm:ss
     *
     * @return date
     */
    public static String getStrSysDefaultFormat() {
        return getStrDate(new Date(), GENERAL_TIME_FORMAT);
    }

    /**
     * 获取默认格式的时间
     *
     * @param time
     * @return
     */
    public static String getStrDefaultFormat(long time) {
        return getStrDefaultFormat(new Date(time));
    }


    /**
     * 获取默认格式的时间
     *
     * @param time 时间
     * @return 返回默认格式的时间字符串
     */
    public static String getStrDefaultFormat(Date time) {
        return getStrDate(time, GENERAL_TIME_FORMAT);
    }

    /**
     * 获取指定格式的系统时间
     *
     * @param format
     * @return
     */
    public static String getStrSysSpecifiedFormat(String format) {
        return getStrDate(new Date(), format);
    }

    /**
     * 检查给定的时间是否在两个时间范围内
     *
     * @param checkDate 给定的时间
     * @param startDate 其实时间
     * @param endDate   终止时间
     * @return
     */
    public static boolean isDoesTimeInclude(Date checkDate, Date startDate, Date endDate) {
        Objects.requireNonNull(checkDate);
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        if (startDate.getTime() < endDate.getTime()) throw new IllegalArgumentException("起始时间小于终止时间");
        return checkDate.getTime() >= startDate.getTime()
                && endDate.getTime() >= checkDate.getTime();
    }


    public static Date getDateDefaultFormat(String date) {
        return getDateConversion(date, GENERAL_TIME_FORMAT);
    }

    /**
     * 获取日期的时间戳
     *
     * @param date
     * @return
     */
    public static long getLongDefaultFormat(String date) {
        return getDateDefaultFormat(date).getTime();
    }

    /**
     * 获取默认的系统时间long，系统时间只到秒
     *
     * @return
     */
    public static long getLongSysDefaultFormat() {
        return getLongDefaultFormat(getStrSysDefaultFormat());
    }


    /**
     * 将String日期转换成Date
     *
     * @return
     */
    private static Date getDateConversion(String date, String... format) {
        try {
            return DateUtils.parseDateStrictly(date, null, format);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取文件名称日期
     *
     * @return
     */
    public static String getStrSysFileName() {
        return getStrSysFileName(new Date());
    }

    public static String getStrSysFileName(Date date) {
        return getStrDate(date, "yyyy-MM-dd HH-mm-ss");
    }

    /**
     * 检查给定的时间加入day天后与检查的是否在同一天
     *
     * @param currentTime       需要相加的天数
     * @param checkDate         需要检查的天数
     * @param currentTimeAddDay 需要添加的天数
     * @return
     */
    public static boolean isSameDay(Date currentTime, Date checkDate, int currentTimeAddDay) {
        return DateUtils.isSameDay(DateUtils.addDays(currentTime, currentTimeAddDay), checkDate);
    }


    /**
     * 两个时间相差多少天
     *
     * @param smdate
     * @param bdate
     * @return
     */
    public static long getDayDiff(Date smdate, Date bdate) {
        long bDay = bdate.getTime() / DateUtils.MILLIS_PER_DAY;
        long aDay = smdate.getTime() / DateUtils.MILLIS_PER_DAY;
        return Math.abs(bDay-aDay);
    }

    public static void main(String[] args) {
        final DateTime date1 =  new DateTime(setZeroTime(new Date()));
        final DateTime dateTime = DateTime.now().plusSeconds(-1);
        System.out.println(date1);
        System.out.println(dateTime);
        System.out.println(getDayDiff(date1.toDate(),dateTime.toDate()));
    }
    /**
     * 设置零时
     *
     * @return
     */
    public static Date setZeroTime(Date date) {
        return new Date(date.getTime() / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset());
    }

//    long current=System.currentTimeMillis();//当前时间毫秒数
//    long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
//    long twelve=zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数
//    long yesterday=System.currentTimeMillis()-24*60*60*1000;//昨天的这一时间的毫秒数

    /**
     * 返回两个时间的相差分钟数
     */
    public static Long getMinuteDiff(Date startTime, Date endTime) {
        Long minutes = null;
        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        long l_s = c.getTimeInMillis();
        c.setTime(endTime);
        long l_e = c.getTimeInMillis();
        minutes = (l_e - l_s) / DateUtils.MILLIS_PER_MINUTE;
        return minutes;
    }

    /**
     * 返回两个时间的相差秒数
     */
    public static Long getSecondDiff(Date startTime, Date endTime) {
        return (endTime.getTime() - startTime.getTime()) / DateUtils.MILLIS_PER_SECOND;
    }

    /**
     * 返回两个时间的相差月数
     */
    public static int getMonthDiff(Date startTime, Date endTime) {
        int months = 0;
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(startTime);
        endCalendar.setTime(endTime);
        months = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        return months;
    }


    /**
     * 检查日期加上多少天后是否在检查日期指定的10分钟范围内
     *
     * @param date      参照日期
     * @param checkData 检查日期
     * @param day       参照日期加入的天数
     * @return
     */
    public static boolean isDateScopeTenMinutes(Date date, Date checkData, int day) {
        return isDateScopeTenMinutes(date, checkData, day, 10);
    }

    /**
     * 检查日期加上多少天后是否在检查日期指定的范围内
     *
     * @param date         参照日期
     * @param checkData    检查日期
     * @param day          参照日期加入的天数
     * @param minutesRange 范围
     * @return
     */
    public static boolean isDateScopeTenMinutes(Date date, Date checkData, int day, int minutesRange) {
        if (day > 0) date = DateUtils.addDays(date, day);
        long less1 = DateUtils.addMinutes(date, -minutesRange).getTime();
        long big1 = DateUtils.addMinutes(date, minutesRange).getTime();
        return checkData.getTime() >= less1 && checkData.getTime() <= big1;
    }

    public static List<Date> getConsecutiveDays(Date startDate, Date endDate) {
        List<Date> dates = new ArrayList<>();
        if (startDate.getTime() > endDate.getTime()) throw new IllegalArgumentException("终止日期不能小于起始日期");
        while (startDate.getTime() <= endDate.getTime()) {
            dates.add(startDate);
            startDate = new DateTime(startDate).plusDays(1).toDate();
        }
        return dates;
    }


    /**
     * 获取最近N个月的日期
     *
     * @param size
     * @return
     */
    public static List<String> getLastMonths(int size, String form) {
        SimpleDateFormat sdf = new SimpleDateFormat(form);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        List<String> list = new ArrayList<>(size);
        for (int i = size - 1; i >= 0; i--) {
            c.setTime(new Date());
            c.add(Calendar.MONTH, -i);
            Date m = c.getTime();
            list.add(sdf.format(m));
        }
        Collections.reverse(list);
        return list;
    }

    public static List<String> getLastMonths(int size) {
        return getLastMonths(size, YYMM);
    }
}
