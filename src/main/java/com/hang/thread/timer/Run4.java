package com.hang.thread.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ZhangHang
 * @create 2017-08-03 15:26
 * TimerTask类中cancel()方法
 * cancel()方法可以将自身TimerTask从队列中移除
 **/
public class Run4 {
    static public class MyTaskA extends TimerTask{
        @Override
        public void run() {
            System.out.println("A运行了！  时间为："+new Date());
            this.cancel();
        }
    }

    static public class MyTaskB extends TimerTask{
        @Override
        public void run() {
            System.out.println("B运行了！  时间为："+new Date());
        }
    }

    public static void main(String[] args) {
        try {
            MyTaskA taskA=new MyTaskA();
            MyTaskB taskB=new MyTaskB();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString="2017-08-03 15:30:40";
            Date dateRef=sdf.parse(dateString);
            System.out.println("字符串时间："+dateRef.toLocaleString()+"当前事件："+new Date());
            Timer timer=new Timer();
            timer.schedule(taskA,dateRef,4000);
            timer.schedule(taskB,dateRef,4000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
