package org.zly.utils.random;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.zly.utils.date.ZlyDateUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class ZlyRandomDate {

    /**
     * 随机获取一个日期
     *
     * @param standardDate 日期基数
     * @param minDay
     * @param maxDay
     * @return
     */
    public static Date nextDate(Date standardDate, int minDay, int maxDay) {
        return nextDate(standardDate, ZlyRandomNumberUtils.nextInt(minDay, maxDay));
    }

    public static Date nextDate(Date standardDate, int day) {
        Objects.requireNonNull(standardDate);
        return new DateTime(standardDate).plusDays(day).toDate();
    }

    public static Date nextDatePost(Date standardDate, int minDay, int maxDay) {
        Objects.requireNonNull(standardDate);
        if (minDay < 0) throw new IllegalArgumentException("minDay必须大于等于0");
        if (maxDay < 0) throw new IllegalArgumentException("maxDay必须大于等于0");
        return nextDate(standardDate, minDay, maxDay);
    }

    public static Date nextDatePre(Date standardDate, int minDay, int maxDay) {
        Objects.requireNonNull(standardDate);
        if (minDay > 0) throw new IllegalArgumentException("minDay必须小于等于0");
        if (maxDay > 0) throw new IllegalArgumentException("maxDay必须小于等于0");
        return nextDate(standardDate, minDay, maxDay);
    }

    public static Date nextDateRange(Date minDate, Date maxDate) {
        Objects.requireNonNull(maxDate);
        if (!minDate.before(maxDate) && !DateUtils.isSameDay(minDate, maxDate))
            throw new IllegalArgumentException("maxDate必须大于等于minDate");
        int day = (int) ZlyDateUtils.getDayDiff(minDate, maxDate);
        return nextDate(minDate, day);
    }

}
