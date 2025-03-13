package wh.future.framework.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import wh.future.framework.common.enums.DateIntervalEnum;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TimeUtils {

    public static final String DEFAULT_TIME_ZONE = "GMT+8";

    public static final long SECOND_MILLIS = 1000;

    public static final String YEAR_MONTH_DAY = "yyyyMMdd";

    public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static Date toDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        ZonedDateTime zonedDateTime = date.atZone(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static Date addTime(Duration duration) {
        return new Date(System.currentTimeMillis() + duration.toMillis());
    }

    public static boolean isExpired(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(time);
    }

    public static Date buildDate(int year, int month, int day) {
        return buildTime(year, month, day, 0, 0, 0);
    }

    public static LocalDateTime buildLocalDateTime(int year, int month, int day) {
        Date date = buildTime(year, month, day, 0, 0, 0);
        return toLocalDateTime(date);
    }

    public static Date buildTime(int year, int mouth, int day,
                                 int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, mouth - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date max(Date a, Date b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        return a.compareTo(b) > 0 ? a : b;
    }

    public static LocalDateTime max(LocalDateTime a, LocalDateTime b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        return a.compareTo(b) > 0 ? a : b;
    }

    public static boolean withInTheInterval(LocalDateTime start, LocalDateTime end, String time) {
        if (start == null || end == null || time == null) {
            return Boolean.FALSE;
        }
        return LocalDateTimeUtil.isIn(parse(time), start, end);
    }

    public static boolean withInTheInterval(LocalDateTime start, LocalDateTime end, LocalDateTime time) {
        if (start == null || end == null || time == null) {
            return Boolean.FALSE;
        }
        return LocalDateTimeUtil.isIn(time, start, end);
    }

    public static boolean nowWithInTheInterval(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return Boolean.FALSE;
        }
        return LocalDateTimeUtil.isIn(LocalDateTime.now(), start, end);
    }

    public static boolean isOverlapping(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }

    public static LocalDateTime beginOfMonth(LocalDateTime date) {
        return date.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    public static LocalDateTime endOfMonth(LocalDateTime date) {
        return date.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
    }

    /**
     * 获取指定时间所在季度
     *
     * @param dateTime
     * @return
     */
    public static int getQuarter(LocalDateTime dateTime) {
        int month = dateTime.getMonthValue();
        return (month - 1) / 3 + 1;
    }

    public static LocalDateTime getToday() {
        return LocalDateTimeUtil.beginOfDay(LocalDateTime.now());
    }

    public static LocalDateTime getYesterday() {
        return LocalDateTimeUtil.beginOfDay(LocalDateTime.now().minusDays(1));
    }

    public static LocalDateTime parse(String time) {
        try {
            return LocalDateTimeUtil.parse(time, DatePattern.NORM_DATE_PATTERN);
        } catch (DateTimeParseException e) {
            return LocalDateTimeUtil.parse(time);
        }
    }

    public static LocalDateTime getFirstDayOfMonth() {
        return beginOfMonth(LocalDateTime.now());
    }

    public static LocalDateTime getFirstDayOfYear() {
        return LocalDateTime.now().with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
    }

    public static List<LocalDateTime[]> getDateRangeList(LocalDateTime startTime,
                                                         LocalDateTime endTime,
                                                         DateIntervalEnum dateIntervalEnum) {
        startTime = LocalDateTimeUtil.beginOfDay(startTime);
        endTime = LocalDateTimeUtil.endOfDay(endTime);
        List<LocalDateTime[]> timeRanges = new ArrayList<>();
        switch (dateIntervalEnum) {
            case DAY:
                while (startTime.isBefore(endTime)) {
                    timeRanges.add(new LocalDateTime[]{startTime, startTime.plusDays(1).minusNanos(1)});
                    startTime = startTime.plusDays(1);
                }
                break;
            case WEEK:
                while (startTime.isBefore(endTime)) {
                    LocalDateTime endOfWeek = startTime.with(DayOfWeek.SUNDAY).plusDays(1).minusNanos(1);
                    timeRanges.add(new LocalDateTime[]{startTime, endOfWeek});
                    startTime = endOfWeek.plusNanos(1);
                }
                break;
            case MONTH:
                while (startTime.isBefore(endTime)) {
                    LocalDateTime endOfMonth = startTime.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1).minusNanos(1);
                    timeRanges.add(new LocalDateTime[]{startTime, endOfMonth});
                    startTime = endOfMonth.plusNanos(1);
                }
                break;
            case QUARTER:
                while (startTime.isBefore(endTime)) {
                    int quarterOfYear = getQuarter(startTime);
                    LocalDateTime quarterEnd = quarterOfYear == 4
                            ? startTime.with(TemporalAdjusters.lastDayOfYear()).plusDays(1).minusNanos(1)
                            : startTime.withMonth(quarterOfYear * 3 + 1).withDayOfMonth(1).minusNanos(1);
                    timeRanges.add(new LocalDateTime[]{startTime, quarterEnd});
                    startTime = quarterEnd.plusNanos(1);
                }
                break;
            case YEAR:
                while (startTime.isBefore(endTime)) {
                    LocalDateTime endOfYear = startTime.with(TemporalAdjusters.lastDayOfYear()).plusDays(1).minusNanos(1);
                    timeRanges.add(new LocalDateTime[]{startTime, endOfYear});
                    startTime = endOfYear.plusNanos(1);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid interval: " + dateIntervalEnum);
        }
        LocalDateTime[] lastTimeRange = CollUtil.getLast(timeRanges);
        if (lastTimeRange != null) {
            lastTimeRange[1] = endTime;
        }
        return timeRanges;
    }

    public static String formatDateRange(LocalDateTime startTime, DateIntervalEnum dateIntervalEnum) {
        switch (dateIntervalEnum) {
            case DAY:
                return LocalDateTimeUtil.format(startTime, DatePattern.NORM_DATE_PATTERN);
            case WEEK:
                return LocalDateTimeUtil.format(startTime, DatePattern.NORM_DATE_PATTERN)
                        + StrUtil.format("(第 {} 周)", LocalDateTimeUtil.weekOfYear(startTime));
            case MONTH:
                return LocalDateTimeUtil.format(startTime, DatePattern.NORM_MONTH_PATTERN);
            case QUARTER:
                return StrUtil.format("{}-Q{}", startTime.getYear(), getQuarter(startTime));
            case YEAR:
                return LocalDateTimeUtil.format(startTime, DatePattern.NORM_YEAR_PATTERN);
            default:
                throw new IllegalArgumentException("Invalid interval: " + dateIntervalEnum);
        }
    }
}
