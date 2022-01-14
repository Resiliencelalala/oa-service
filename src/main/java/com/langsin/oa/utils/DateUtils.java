package com.langsin.oa.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Month;
import cn.hutool.core.date.Week;
import cn.hutool.core.date.format.FastDateFormat;
import cn.hutool.core.util.StrUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期时间工具类.
 *
 * @author wyy
 * @date 2019/11/11
 */
public class DateUtils {

    /**
     * 当前时间
     *
     * @return {@link Date}
     */
    public static Date date() {
        return new DateTime();
    }

    /**
     * Long类型时间转为{@link Date}<br>
     * 同时支持10位秒级别时间戳和13位毫秒级别时间戳
     *
     * @param date Long类型Date（Unix时间戳）
     * @return 时间对象
     */
    public static Date date(long date) {
        return new DateTime(date);
    }

    /**
     * {@link Calendar}类型时间转为{@link Date}
     *
     * @param calendar {@link Calendar}
     * @return 时间对象
     */
    public static Date date(Calendar calendar) {
        return new DateTime(calendar);
    }

    /**
     * 当前时间，格式 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间的标准形式字符串
     */
    public static String now() {
        return DateUtil.formatDateTime(new DateTime());
    }

    /**
     * 根据字符串返回Date yyyy-MM-dd HH:mm:ss
     *
     * @param strDate 时间字符串
     * @return date
     */
    public static Date parseDateTime(String strDate) {
        return DateUtil.parseDateTime(strDate);
    }

    /**
     * 根据字符串返回Date yyyy-MM-dd HH:mm:ss
     *
     * @param strDate 时间字符串
     * @return date
     */
    public static Date parseDate(String strDate) {
        return DateUtil.parseDate(strDate);
    }

    /**
     * 当前时间long
     *
     * @param isNano 是否为高精度时间
     * @return 时间
     */
    public static long current(boolean isNano) {
        return isNano ? System.nanoTime() : System.currentTimeMillis();
    }

    /**
     * 当前时间秒数
     *
     * @return 当前时间秒数
     */
    public static long currentSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取 现在 到指定日期的秒数
     *
     * @param date
     * @return
     */
    public static long diffSeconds(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDateTime(date));
        Long intval = (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000;
        return intval;
    }

    /**
     * 将世界标准时间转换为目标时区的本地时间
     *
     * @param gmtDate
     * @param id
     * @return
     */
    public static Date convertGMTToLocal(Date gmtDate, String id) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(gmtDate);
        calendar.setTimeZone(TimeZone.getTimeZone(id));
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND, dstOffset + zoneOffset);
        return calendar.getTime();
    }

    /**
     * 当前日期，格式 yyyy-MM-dd
     *
     * @return 当前日期的标准形式字符串
     */
    public static String today() {
        return DateUtil.formatDate(new DateTime());
    }

    /**
     * 当前日期，格式 yyyyMMdd
     *
     * @return 当前日期的标准形式字符串
     */
    public static String todaySerial() {
        return DateUtil.format(new DateTime(), "yyyyMMdd");
    }

    public static String todaySerial2() {
        return DateUtil.format(new DateTime(), "yyyyMMddHHmmss");
    }

    /**
     * 获取 日期中的时分秒
     *
     * @param date
     * @return
     */
    public static String hourMinuteSecond(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 当前日期，格式 Date yyyy-MM-dd
     *
     * @return 当前日期的标准形式字符串
     */
    public static Date todayToDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long currentTime = System.currentTimeMillis();
        String strDate = sdf.format(currentTime);
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date millisecondToDate(Long millisecond) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(millisecond);
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取某月 第一天 和最后一天
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static String[] timeQuantum(String time) {
        String[] dates = new String[2];
        String start;
        String end;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        assert date != null;
        c.setTime(date);
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, lastDay);
        start = sdf.format(date);
        end = sdf.format(c.getTime());
        dates[0] = start;
        dates[1] = end;
        return dates;
    }

    /**
     * 获取某月 第一天 和最后一天
     */
    public static Date[] firstDateAndLastDate(Date date) {
        Date[] dates = new Date[2];
        Calendar c = Calendar.getInstance();
        Date firstDate = firstDay(date);
        c.setTime(firstDate);
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, lastDay);
        dates[0] = firstDate;
        String endDate = DateUtil.formatDate(c.getTime());
        dates[1] = DateUtil.parseDate(endDate);
        return dates;
    }

    /**
     * 获取指定月的天数
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取每月第一天
     *
     * @param date 日期
     * @return date
     */
    public static Date firstDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        String dateStr = DateUtil.formatDate(c.getTime());
        return DateUtil.parseDate(dateStr);
    }

    /**
     * 获取每月第一天 (字符串)
     *
     * @param date 日期
     * @return date
     */
    public static String firstDayToString(String date) {
        Calendar c = Calendar.getInstance();
        c.setTime(DateUtil.parseDate(date));
        c.set(Calendar.DAY_OF_MONTH, 1);
        return DateUtil.formatDate(c.getTime());
    }

    /**
     * 获取每月最后一天 (字符串)
     *
     * @param date 日期
     * @return date
     */
    public static String lastDayToString(String date) {
        Calendar c = Calendar.getInstance();
        c.setTime(DateUtil.parseDate(date));
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.DATE, -1);
        return DateUtil.formatDate(c.getTime());
    }

    /**
     * 获取每月最后一天 (Date)
     *
     * @param date 日期
     * @return date
     */
    public static Date lastDayToDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.DATE, -1);
        String dateStr = DateUtil.formatDate(c.getTime());
        return DateUtil.parseDate(dateStr);
    }

    public static Date getPrevious() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        String dateStr = DateUtil.formatDate(c.getTime());
        return DateUtil.parseDate(dateStr);
    }
    // -------------------------------------------------------------- Part of Date start

    /**
     * 获得年的部分
     *
     * @param date 日期
     * @return 年的部分
     */
    public static int year(Date date) {
        return DateTime.of(date).year();
    }

    /**
     * 获得指定日期所属季度
     *
     * @param date 日期
     * @return 第几个季度
     */
    public static int season(Date date) {
        return DateTime.of(date).season();
    }

    /**
     * 获得月份，从0开始计数
     *
     * @param date 日期
     * @return 月份，从0开始计数
     */
    public static int month(Date date) {
        return DateTime.of(date).month();
    }

    /**
     * 获得月份
     *
     * @param date 日期
     * @return {@link Month}
     */
    public static int monthEnum(Date date) {
        return DateTime.of(date).monthEnum().getValue();
    }

    /**
     * 获得指定日期是所在年份的第几周<br>
     *
     * @param date 日期
     * @return 周
     */
    public static int weekOfYear(Date date) {
        return DateTime.of(date).weekOfYear();
    }

    /**
     * 获得指定日期是所在月份的第几周<br>
     *
     * @param date 日期
     * @return 周
     */
    public static int weekOfMonth(Date date) {
        return DateTime.of(date).weekOfMonth();
    }

    /**
     * 获得指定日期是这个日期所在月份的第几天<br>
     *
     * @param date 日期
     * @return 天
     */
    public static int dayOfMonth(Date date) {
        return DateTime.of(date).dayOfMonth();
    }

    /**
     * 获得指定日期是星期几
     *
     * @param date 日期
     * @return 天
     */
    public static int dayOfWeek(Date date) {
        return DateTime.of(date).dayOfWeek();
    }

    /**
     * 获得指定日期是星期几
     *
     * @param date 日期
     * @return {@link Week}
     */
    public static int dayOfWeekEnum(Date date) {
        return DateTime.of(date).dayOfWeekEnum().getValue();
    }

    /**
     * 获得指定日期的小时数部分<br>
     *
     * @param date          日期
     * @param is24HourClock 是否24小时制
     * @return 小时数
     */
    public static int hour(Date date, boolean is24HourClock) {
        return DateTime.of(date).hour(is24HourClock);
    }

    /**
     * 获得指定日期的分钟数部分<br>
     * 例如：10:04:15.250 =》 4
     *
     * @param date 日期
     * @return 分钟数
     */
    public static int minute(Date date) {
        return DateTime.of(date).minute();
    }

    /**
     * 获得指定日期的秒数部分<br>
     *
     * @param date 日期
     * @return 秒数
     */
    public static int second(Date date) {
        return DateTime.of(date).second();
    }

    /**
     * 获得指定日期的毫秒数部分<br>
     *
     * @param date 日期
     * @return 毫秒数
     */
    public static int millsecond(Date date) {
        return DateTime.of(date).millsecond();
    }

    /**
     * 是否为上午
     *
     * @param date 日期
     * @return 是否为上午
     */
    public static boolean isAM(Date date) {
        return DateTime.of(date).isAM();
    }

    /**
     * 是否为下午
     *
     * @param date 日期
     * @return 是否为下午
     */
    public static boolean isPM(Date date) {
        return DateTime.of(date).isPM();
    }

    /**
     * @return 今年
     */
    public static int thisYear() {
        return year(date());
    }

    /**
     * @return 当前月份
     */
    public static int thisMonth() {
        return month(date());
    }

    /**
     * @return 当前月份 {@link Month}
     */
    public static int thisMonthEnum() {
        return monthEnum(date());
    }

    /**
     * @return 当前日期所在年份的第几周
     */
    public static int thisWeekOfYear() {
        return weekOfYear(date());
    }

    /**
     * @return 当前日期所在年份的第几周
     */
    public static int thisWeekOfMonth() {
        return weekOfMonth(date());
    }

    /**
     * @return 当前日期是这个日期所在月份的第几天
     */
    public static int thisDayOfMonth() {
        return dayOfMonth(date());
    }

    /**
     * @return 当前日期是星期几
     */
    public static int thisDayOfWeek() {
        return dayOfWeek(date());
    }

    /**
     * @return 当前日期是星期几 {@link Week}
     */
    public static int thisDayOfWeekEnum() {
        return dayOfWeekEnum(date());
    }

    /**
     * @param is24HourClock 是否24小时制
     * @return 当前日期的小时数部分<br>
     */
    public static int thisHour(boolean is24HourClock) {
        return hour(date(), is24HourClock);
    }

    /**
     * @return 当前日期的分钟数部分<br>
     */
    public static int thisMinute() {
        return minute(date());
    }

    /**
     * @return 当前日期的秒数部分<br>
     */
    public static int thisSecond() {
        return second(date());
    }

    /**
     * @return 当前日期的毫秒数部分<br>
     */
    public static int thisMillsecond() {
        return millsecond(date());
    }
    // -------------------------------------------------------------- Part of Date end

    /**
     * 根据特定格式格式化日期
     *
     * @param date   被格式化的日期
     * @param format 日期格式，常用格式见： {@link cn.hutool.core.date.DatePattern}
     * @return 格式化后的字符串
     */
    public static String format(Date date, String format) {
        if (null == date || StrUtil.isBlank(format)) {
            return null;
        }
        return DateUtil.format(date, FastDateFormat.getInstance(format));
    }

    /**
     * 获取指定日期的前一天
     *
     * @param date
     * @return
     */
    public static Date prox(Date date) {
        Calendar c = DateUtil.calendar(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        return c.getTime();
    }

    /**
     * 根据特定格式格式化日期
     *
     * @param localDateTime 被格式化的日期
     * @param format        日期格式，常用格式见： {@link cn.hutool.core.date.DatePattern}
     * @return 格式化后的字符串
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime, String format) {
        if (null == localDateTime || StrUtil.isBlank(format)) {
            return null;
        }

        return DateUtil.format(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()), FastDateFormat.getInstance(format));
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
