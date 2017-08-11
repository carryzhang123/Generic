package com.sanqi.Thread.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author ZhangHang
 * @create 2017-08-08 14:17
 **/
public class Run1 {
    public static void main(String[] args){
//        ExecutorService executorService1= Executors.newSingleThreadExecutor();
//        ExecutorService executorService= Executors.newFixedThreadPool(6);
//        ExecutorService executorService=Executors.newCachedThreadPool();
//        ScheduledExecutorService executorService=Executors.newScheduledThreadPool(5);
        ScheduledExecutorService executorService=Executors.newSingleThreadScheduledExecutor();
        MyTask myTask=new MyTask();
        MyTask myTask1=new MyTask();
        MyTask myTask2=new MyTask();
        MyTask myTask3=new MyTask();
        MyTask myTask4=new MyTask();
        MyTask myTask5=new MyTask();

        executorService.execute(myTask);
        executorService.execute(myTask1);
        executorService.execute(myTask2);
        executorService.execute(myTask3);
        executorService.execute(myTask4);
        executorService.execute(myTask5);
        executorService.shutdown();
    }
}

class MyTask implements Runnable{
    @Override
    public void run() {
        System.out.println("当前Thread："+Thread.currentThread().getId()+"  name："+Thread.currentThread().getName());
    }
}
