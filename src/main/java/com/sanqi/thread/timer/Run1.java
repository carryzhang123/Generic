package com.sanqi.thread.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ZhangHang
 * @create 2017-08-03 12:07
 * 一个Timer相当于启动了一个新的线程，即要在Timer的TimerTask任务里面重写run方法，以便Timer中的线程去执行TimerTask里的任务
 * Timer会按照传入的时间，等到指定时间后，会立即执行Task任务类
 **/
public class Run1 {
    private static Timer timer=new Timer();//创建一个Timer相当于启动一个新的线程
    static public class MyTask extends TimerTask{
        @Override
        public void run() {
            System.out.println("运行了！ 时间为："+new Date());
        }
    }

    public static void main(String[] args) {
        try {
            MyTask task=new MyTask();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString="2017-08-03 12:10:00";
            Date dateRef=sdf.parse(dateString);
            System.out.println("字符串时间："+dateRef.toLocaleString()+"当前事件："+new Date());
            //计划时间早于当前时间：立即执行task任务
            timer.schedule(task,dateRef);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
}
