package com.session.utils;

import com.session.constants.Constants;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 注意事项，调整：
 * 在大数据项目中， 不要使用SimpleDateFormat类对时间进行处理，
 * 因为SimpleDateFormat类是线程不安全的。
 * 而应该使用FastDateFormat类(org.apache.commons.lang3.time.FastDateFormat)。
 * 否则，单机测试的时候，可能不会出现问题，但是当在集群中运行的时候，
 * 就会出现各种运行时异常，而这种异常很难排查，因为逻辑都是对的。
 */
public class DateUtils {

    public static final SimpleDateFormat TIME_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATEKEY_FORMAT =
            new SimpleDateFormat("yyyyMMdd");

    /**
     * 判断一个时间是否在另一个时间之前
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 判断结果
     */
    public static boolean before(String time1, String time2) {
        try {
            Date dateTime1 = TIME_FORMAT.parse(time1);
            Date dateTime2 = TIME_FORMAT.parse(time2);

            if(dateTime1.before(dateTime2)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断一个时间是否在另一个时间之后
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 判断结果
     */
    public static boolean after(String time1, String time2) {
        try {
            Date dateTime1 = TIME_FORMAT.parse(time1);
            Date dateTime2 = TIME_FORMAT.parse(time2);

            if(dateTime1.after(dateTime2)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 计算时间差值（单位为秒）
     * @param time1 时间1
     * @param time2 时间2
     * @return 差值
     */
    public static int minus(String time1, String time2) {
        try {
            Date datetime1 = TIME_FORMAT.parse(time1);
            Date datetime2 = TIME_FORMAT.parse(time2);

            long millisecond = datetime1.getTime() - datetime2.getTime();

            return Integer.valueOf(String.valueOf(millisecond / 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取年月日和小时
     * @param datetime 时间（yyyy-MM-dd HH:mm:ss）
     * @return 结果（yyyy-MM-dd_HH）
     */
    public static String getDateHour(String datetime) {
        String date = datetime.split(" ")[0];
        String hourMinuteSecond = datetime.split(" ")[1];
        String hour = hourMinuteSecond.split(":")[0];
        return date + "_" + hour;
    }

    /**
     * 获取当天日期（yyyy-MM-dd）
     * @return 当天日期
     */
    public static String getTodayDate() {
        return DATE_FORMAT.format(new Date());
    }

    /**
     * 获取昨天的日期（yyyy-MM-dd）
     * @return 昨天的日期
     */
    public static String getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, -1);

        Date date = cal.getTime();

        return DATE_FORMAT.format(date);
    }

    /**
     * 格式化日期（yyyy-MM-dd）
     * @param date Date对象
     * @return 格式化后的日期
     */
    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    /**
     * 格式化时间（yyyy-MM-dd HH:mm:ss）
     * @param date Date对象
     * @return 格式化后的时间
     */
    public static String formatTime(Date date) {
        return TIME_FORMAT.format(date);
    }

    /**
     * 解析时间字符串
     * @param time 时间字符串
     * @return Date
     */
    public static Date parseTime(String time) {
        try {
            return TIME_FORMAT.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化日期key
     * @param date
     * @return
     */
    public static String formatDateKey(Date date) {
        return DATEKEY_FORMAT.format(date);
    }

    /**
     * 格式化日期key
     * @param datekey
     * @return
     */
    public static Date parseDateKey(String datekey) {
        try {
            return DATEKEY_FORMAT.parse(datekey);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化时间，保留到分钟级别
     * yyyyMMddHHmm
     * @param date
     * @return
     */
    public static String formatTimeMinute(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        return sdf.format(date);
    }


    /**
     * 判断输入字符串是否符合日期规范，返回值Boolean
     * 格式：yyyy-MM-dd
     * 符合规范返回ture,否则返回false
     */
    public static Boolean isFormat(String date){
        //定义时间格式2017-06-30
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_YEAR_MONTH_DAY);
        //判断是否符合格式
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    /**
     * 更加全面的format格式判断
     */
    public static Boolean is_Format(String date, String format){
        //定义时间格式比如2017-06-30
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        //判断是否符合格式
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    /**
     * 将时间字符串转换成指定格式的时间
     */
    public static Date parseTime(String time, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            return new Date();
        }
    }
    /**
     * 判断一个时间字符串是否在另外一个时间字符串之前
     * param:time1,time2即为两个需要比较的时间
     * param:format即为时间的格式
     */
    public static boolean before(String time1, String time2, String format){
        Boolean is_before = false;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            if(date1.before(date2)){
                is_before = true;
            }
        } catch (ParseException e) {
            return  false;
        }
        return is_before;
    }
    /**
     * 判断一个时间字符串是否在另外一个时间字符串之后
     * param:time1,time2即为两个需要比较的时间
     * param:format即为时间的格式
     */
    public static boolean after(String time1, String time2, String format){
        Boolean is_after = false;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            if(date1.after(date2)){
                is_after = true;
            }
        } catch (ParseException e) {
            return  false;
        }
        return is_after;
    }
    /**
     * 计算两个时间字符串的差值，单位为分
     * param:time1,time2即为两个需要计算的时间，默认time1>time2
     * param:format即为时间的格式
     */
    public static int getMinuteOfTwoTime(String time1,String time2, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        int minute = 0;
        try {
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            long millisecond = date1.getTime() - date2.getTime();
            minute = Integer.valueOf(String.valueOf(millisecond/60000));
            return minute;
        } catch (ParseException e) {
            return  minute;
        }
    }

    /**
     * 季度日期类操作
     */

    public static final String DATE_FORMAT2 = "yyyy-MM-dd";

    /**
     * 获取昨日的日期格式字符串数据
     *
     * @return
     */
    public static String getYesterday() {
        return getYesterday(DATE_FORMAT2);
    }

    /**
     * 获取对应格式的时间字符串
     *
     * @param pattern
     * @return
     */
    public static String getYesterday(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return sdf.format(calendar.getTime());
    }

    /**
     * 判断输入的参数是否是一个有效的时间格式数据
     *
     * @param input
     * @return
     */
    public static boolean isValidateRunningDate(String input) {
        Matcher matcher = null;
        boolean result = false;
        String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        if (input != null && !input.isEmpty()) {
            Pattern pattern = Pattern.compile(regex);
            matcher = pattern.matcher(input);
        }
        if (matcher != null) {
            result = matcher.matches();
        }
        return result;
    }

    /**
     * 将yyyy-MM-dd格式的时间字符串转换为时间戳
     *
     * @param input
     * @return
     */
    public static long parseString2Long(String input) {
        return parseString2Long(input, DATE_FORMAT2);
    }

    /**
     * 将指定格式的时间字符串转换为时间戳
     *
     * @param input
     * @param pattern
     * @return
     */
    public static long parseString2Long(String input, String pattern) {
        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(input);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date.getTime();
    }

    /**
     * 将时间戳转换为yyyy-MM-dd格式的时间字符串
     *
     * @param input
     * @return
     */
    public static String parseLong2String(long input) {
        return parseLong2String(input, DATE_FORMAT2);
    }

    /**
     * 将时间戳转换为指定格式的字符串
     *
     * @param input
     * @param pattern
     * @return
     */
    public static String parseLong2String(long input, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(input);
        return new SimpleDateFormat(pattern).format(calendar.getTime());
    }

    /**
     * 将nginx服务器时间转换为时间戳，如果说解析失败，返回-1
     *
     * @param input
     * @return
     */
    public static long parseNginxServerTime2Long(String input) {
        Date date = parseNginxServerTime2Date(input);
        return date == null ? -1L : date.getTime();
    }

    /**
     * 将nginx服务器时间转换为date对象。如果解析失败，返回null
     *
     * @param input
     *            格式: 1449410796.976
     * @return
     */
    public static Date parseNginxServerTime2Date(String input) {
        if (StringUtils.isNotBlank(input)) {
            try {
                long timestamp = Double.valueOf(Double.valueOf(input.trim()) * 1000).longValue();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timestamp);
                return calendar.getTime();
            } catch (Exception e) {
                // nothing
            }
        }
        return null;
    }

    /**
     * 从时间戳中获取需要的时间信息
     *
     * @param time
     *            时间戳
     * @param type
     * @return 如果没有匹配的type，抛出异常信息
     */
    public static int getDateInfo(long time, DateEnum type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        if (DateEnum.YEAR.equals(type)) {
            // 需要年份信息
            return calendar.get(Calendar.YEAR);
        } else if (DateEnum.SEASON.equals(type)) {
            // 需要季度信息
            int month = calendar.get(Calendar.MONTH) + 1;
            if (month % 3 == 0) {
                return month / 3;
            }
            return month / 3 + 1;
        } else if (DateEnum.MONTH.equals(type)) {
            // 需要月份信息
            return calendar.get(Calendar.MONTH) + 1;
        } else if (DateEnum.WEEK.equals(type)) {
            // 需要周信息
            return calendar.get(Calendar.WEEK_OF_YEAR);
        } else if (DateEnum.DAY.equals(type)) {
            return calendar.get(Calendar.DAY_OF_MONTH);
        } else if (DateEnum.HOUR.equals(type)) {
            return calendar.get(Calendar.HOUR_OF_DAY);
        }
        throw new RuntimeException("没有对应的时间类型:" + type);
    }

    /**
     * 获取time指定周的第一天的时间戳值
     *
     * @param time
     * @return
     */
    public static long getFirstDayOfThisWeek(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }


}