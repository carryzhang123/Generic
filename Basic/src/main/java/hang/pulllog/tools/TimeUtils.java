package hang.pulllog.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author ZhangHang
 * @create 2018-05-08 15:27
 **/
public class TimeUtils {
    public static Calendar getCalendar(String dateStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Calendar nextCalendar(Calendar calendar) {
        Calendar calendarNew = Calendar.getInstance();
        calendarNew.setTimeInMillis(calendar.getTimeInMillis());
        calendarNew.add(Calendar.DAY_OF_MONTH, 1);
        return calendarNew;
    }

    public static String longTimeFormate(long time) {
        Calendar calendarNew = Calendar.getInstance();
        calendarNew.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
        return sdf.format(calendarNew.getTime());
    }

    public static String urlTimeFormate(long time) {
        Calendar calendarNew = Calendar.getInstance();
        calendarNew.setTimeInMillis(time);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat1.format(calendarNew.getTime());
    }

    public static String fileNameByNowDate() {
        Date date = new Date();
        Random rand = new Random();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss"+"-"+rand.nextInt(10000));
        return df.format(date);
    }
}
