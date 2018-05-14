package com.hang.tools.time;

import org.springframework.scheduling.support.CronSequenceGenerator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author ZhangHang
 * @create 2018-01-12 15:13
 **/
public class TimeUtils {

    /**
     * 统一字符串时间格式模版
     */
    private static final String TIME_FORMAT_TEMPLATE = "yyyy-MM-dd HH:mm:ss";
    private static final String datePattern = "yyyy-MM-dd  HH:mm:ss";

    private static final ThreadLocal<SimpleDateFormat> format = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(TIME_FORMAT_TEMPLATE);
        }
    };


    /**
     * 一秒的毫秒数
     */
    public static final long ONE_SECOND = 1 * 1000;
    /**
     * 一分钟的毫秒数
     */
    public static final long ONE_MINUTE = 1 * 60 * ONE_SECOND;
    /**
     * 一小时的毫秒数
     */
    public static final long ONE_HOUR = 60 * ONE_MINUTE;
    /**
     * 一天的毫秒数
     */
    public static final long ONE_DAY = 1 * 24 * ONE_HOUR;

    public static long now() {
        return System.currentTimeMillis();
    }


    /**
     * 获得当天结束的时间戳
     *
     * @return
     */
    public long getTodayEndMills() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);
        today.set(Calendar.MILLISECOND, 999);
        return today.getTimeInMillis();
    }


    /**
     * 获得当天开始的时间戳
     *
     * @return
     */
    public long getTodayStartMills() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today.getTimeInMillis();
    }

    /**
     * 格式化Date时间
     *
     * @param date
     */
    public static String formatTime(Date date) {
        String str = new SimpleDateFormat(datePattern).format(date);
        return str;
    }


    /**
     * 判断c2是否是c1的下一天
     *
     * @param c1
     * @param c2
     */
    public void isNextDay(Calendar c1, Calendar c2) {
        c2.add(Calendar.DAY_OF_MONTH, -1);
        isSameDay(c1, c2);
    }

    /**
     * 是否是今天
     *
     * @param cal
     */
    public void isToday(Calendar cal) {
        Calendar today = Calendar.getInstance();
        isSameDay(today, cal);
    }

    /**
     * 更加Date判断是否处于同一天
     *
     * @param t1
     * @param t2
     */
    public void isSameDay(Date t1, Date t2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(t1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(t2);
        isSameDay(c1, c2);
    }


    /**
     * 根据时间戳判断是否是同一天
     *
     * @param t1
     * @param t2
     */
    public void isSameDay(long t1, long t2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(t1);
        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(t2);
        isSameDay(c1, c2);
    }


    /**
     * c1和c2是否是同一天
     *
     * @param c1
     * @param c2
     */
    public void isSameDay(Calendar c1, Calendar c2) {
        if ((c1.get(Calendar.ERA) == c2.get(Calendar.ERA)) && (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) &&
                c2.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) {
            System.out.println("处于同一天！");
        } else {
            System.out.println("没有处于同一天！");
        }
    }

    /**
     * 延迟多少秒后的具体时间s
     * @param second
     * @return
     */
    public static Date delaySecond(int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    public static Date getNextTime(String cron, Date now) {
        CronSequenceGenerator gen = new CronSequenceGenerator(cron, TimeZone.getDefault());
        Date time = gen.next(now);
        return time;
    }

    public static String date2String(Date date, String pattern) {
        return (new SimpleDateFormat(pattern)).format(date);
    }
}
