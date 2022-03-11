package org.zly.utils.random;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.zly.utils.date.ZlyDateUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class ZlyRandomDateUtils {

    /**
     * 随机获取一个日期
     *
     * @param standardDate 日期基数
     * @param minDay
     * @param maxDay
     * @return
     */
    public static Date nextDay(Date standardDate, int minDay, int maxDay) {
        return ZlyDateUtils.plusDays(standardDate, ZlyRandomNumberUtils.nextInt(minDay, maxDay));
    }

    public static RandomDate nextDayRange(int minDay, int maxDay) {
        return nextDayRange(new Date(), minDay, maxDay);
    }

    public static RandomDate nextDayRange(Date standardDate, int minDay, int maxDay) {
        final RandomDate randomDate = new RandomDate();
        randomDate.setStartDate(standardDate);
        randomDate.setEndDate(nextDayPost(standardDate, minDay, maxDay));
        return randomDate;
    }


    public static Date nextDayPost(Date standardDate, int minDay, int maxDay) {
        Objects.requireNonNull(standardDate);
        if (minDay < 0) throw new IllegalArgumentException("minDay必须大于等于0");
        if (maxDay < 0) throw new IllegalArgumentException("maxDay必须大于等于0");
        return nextDay(standardDate, minDay, maxDay);
    }

    public static Date nextDayPre(Date standardDate, int minDay, int maxDay) {
        Objects.requireNonNull(standardDate);
        if (minDay > 0) throw new IllegalArgumentException("minDay必须小于等于0");
        if (maxDay > 0) throw new IllegalArgumentException("maxDay必须小于等于0");
        return nextDay(standardDate, minDay, maxDay);
    }

    public static Date nextDayRange(Date minDate, Date maxDate) {
        Objects.requireNonNull(maxDate);
        int day = (int) ZlyDateUtils.getDayDiff(minDate, maxDate,false);
        if(day<0)throw new IllegalArgumentException("maxDate必须大于等于minDate");
        return nextDayPost(minDate, 0, day);
    }

    public static Date nextBirth() {
        return nextDayRange(new DateTime("1910-01-01").toDate(), new Date());
    }

}
