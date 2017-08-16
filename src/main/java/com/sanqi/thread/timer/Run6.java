package com.sanqi.thread.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ZhangHang
 * @create 2017-08-03 16:45
 * schedule(TimerTask task , long delay)
 * 以执行schedule方法的当前时间为参考时间，在此时间基础上延迟指定的毫秒去执行TimerTask的任务
 *
 *  schedule(TimerTask task , long delay,long period)
 * 以执行schedule方法的当前时间为参考时间，在此时间基础上延迟指定的毫秒去执行TimerTask的任务，并以period的周期去循环
 **/
public class Run6 {
    static public class MyTaskA extends TimerTask{
        @Override
        public void run() {
            System.out.println("A运行了！  时间为："+new Date());
        }
    }

    static public class MyTaskB extends TimerTask{
        @Override
        public void run() {
            System.out.println("B运行了！  时间为："+new Date());
        }
    }

    public static void main(String[] args) {
        MyTaskA taskA=new MyTaskA();
        MyTaskB taskB=new MyTaskB();
        Timer timer=new Timer();
        System.out.println("当前时间： "+new Date().toLocaleString());
        timer.schedule(taskA,5000);
        timer.schedule(taskB,7000,3000);
    }
}
