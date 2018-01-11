package com.hang.thread.executor;

import java.util.concurrent.*;

/**
 * @author ZhangHang
 * @create 2017-08-08 15:28
 **/
public class ThreadPoolRun {
    public static void main(String[] args) {
        int corePoolSize=5;//核心线程数
        int maxmunPoolSize=10;//最大线程数
        BlockingQueue<Runnable> queue=new ArrayBlockingQueue<Runnable>(20);//创建等待队列
        ThreadPoolExecutor executor=new ThreadPoolExecutor(corePoolSize, maxmunPoolSize, 2, TimeUnit.SECONDS, queue);
        Thread thread1=new Thread(new Task());
        Thread thread2=new Thread(new Task());
        Thread thread3=new Thread(new Task());
        Thread thread4=new Thread(new Task());
        executor.execute(thread1);
        executor.execute(thread2);
        executor.execute(thread3);
        executor.execute(thread4);
        executor.shutdown();
    }
}

class Task implements Runnable{
    @Override
    public void run() {
        System.out.println("线程池使用----"+"id："+Thread.currentThread().getId()+"---name："+Thread.currentThread().getName());
    }
}
