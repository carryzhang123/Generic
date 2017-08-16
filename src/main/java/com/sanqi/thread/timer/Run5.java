package com.sanqi.thread.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ZhangHang
 * @create 2017-08-03 15:47
 * Timer的cancel()方法，会取消所有的TimerTask任务
 * 有时不一定会消除，因为Timer中的cancel()方法有时并没有争抢到queue锁
 **/
public class Run5 {
    private static Timer timer = new Timer();

    static public class MyTaskA extends TimerTask {
        @Override
        public void run() {
            System.out.println("A 运行了！  时间为：" + new Date());
            timer.cancel();
        }
    }

    static public class MyTaskB extends TimerTask {
        @Override
        public void run() {
            System.out.println("B运行了！  时间为：" + new Date());
        }
    }

    public static void main(String[] args) {
        try {
            Run4.MyTaskA taskA = new Run4.MyTaskA();
            Run4.MyTaskB taskB = new Run4.MyTaskB();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = "2017-08-03 15:59:00";
            Date dateRef = sdf.parse(dateString);
            System.out.println("字符串时间：" + dateRef.toLocaleString() + "当前事件：" + new Date());
            Timer timer = new Timer();
            timer.schedule(taskA, dateRef, 4000);
            timer.schedule(taskB, dateRef, 4000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
