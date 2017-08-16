package com.sanqi.thread.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ZhangHang
 * @create 2017-08-03 14:59
 * TimerTask是以列队的方式一个一个的被顺序执行，如果前面一个任务的时间过长，超出了后面的任务的执行时间，则当前面任务执行完后，则立刻
 * 执行后面的任务，相当于如果TimerTask的执行时间在当前时间的前面，则立刻执行
 **/
public class Run2Later {
    private static Timer timer=new Timer();
    static public class MyTask1 extends TimerTask{
        @Override
        public void run() {
            try {
                System.out.println("1 begin 运行了！  时间为："+new Date());
                Thread.sleep(20000);
                System.out.println("1 end   运行了！  时间为："+new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static public class MyTask2 extends TimerTask{
        @Override
        public void run() {
            System.out.println("2 begin 运行了！  时间为："+new Date());
            System.out.println("运行了！  时间为："+new Date());
            System.out.println("2 end   运行了！ 时间为："+new Date());
        }
    }

    public static void main(String[] args) {
        try {
            MyTask1 task1=new MyTask1();
            MyTask2 task2=new MyTask2();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String st1 = "2017-08-03 15:07:00";
            String st2 = "2017-08-03 15:07:10";
            Date dateRef1=sdf1.parse(st1);
            Date dateRef2=sdf2.parse(st2);
            System.out.println("字符串1时间："+dateRef1.toLocaleString()+"---当前时间："+new Date().toLocaleString());
            System.out.println("字符串2时间："+dateRef2.toLocaleString()+"---当前时间："+new Date().toLocaleString());
            timer.schedule(task1,dateRef1);
            timer.schedule(task2,dateRef2);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
}
