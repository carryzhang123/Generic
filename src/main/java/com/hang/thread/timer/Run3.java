package com.hang.thread.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *    @author ZhangHang
 *    @create 2017-08-03 15:13
 *   方法schedule(TimerTask task,Date firstTime,long period)
 *   该方法作用为在指定的日期之后，会按照指定的时间间隔去循环执行
 **/
public class Run3 {
    static public class MyTask extends TimerTask{
        @Override
        public void run() {
            System.out.println("运行了！  时间为："+new Date());
        }
    }

    public static void main(String[] args) {
        try {
            MyTask task=new MyTask();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString="2017-08-03 15:18:00";
            Date dateRef=sdf.parse(dateString);
            System.out.println("字符串时间："+dateRef.toLocaleString()+"当前事件："+new Date());
            Timer timer=new Timer();
            timer.schedule(task,dateRef,4000);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
}
