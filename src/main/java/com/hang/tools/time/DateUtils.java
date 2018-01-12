package com.hang.tools.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ZhangHang
 * @create 2018-01-12 15:13
 **/
public class DateUtils {

    private static final String datePattern = "yyyy-MM-dd HH:mm:ss";


    /**
     * 获得当天结束的时间戳
     * @return
     */
    public long getTodayEndMills(){
        Calendar today=Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY,23);
        today.set(Calendar.MINUTE,59);
        today.set(Calendar.SECOND,59);
        today.set(Calendar.MILLISECOND,999);
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
    public void formatTim(Date date) {
        date = new Date();
        System.out.println(date);
        String str = new SimpleDateFormat(datePattern).format(date);
        System.out.println(str);
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
}
