package com.hang.thread.executor;

import java.util.concurrent.*;

/**
 * @author ZhangHang
 * @create 2017-08-21 11:36
 **/
public class ScheduledThreadPool {
    /**
     * ScheduledExecutorService接口实现了ExecutorService接口
     * ScheduledThreadPoolExecutor继承了ThreadPoolExecutor类
     *
     * @param args
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledThreadPool scheduledThreadPool = new ScheduledThreadPool();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
//        scheduledThreadPool.poolRun(executorService);
        scheduledThreadPool.poolCall(executorService);
    }

    public void poolRun(ScheduledExecutorService executors) {
        for (int i = 0; i < 2; i++) {
            /**
             * scheduleAtFixedRate可以确定延迟时间及轮询间隔
             */
            executors.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(20);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "--flag = " + Thread.currentThread().getId());
                    System.out.println("------------" + "轮询" + "-----------");
                }
            }, 5, 2, TimeUnit.SECONDS);
            System.out.println("-------" + "循环" + "--------");
        }
    }

    public void poolCall(ScheduledExecutorService executorService) throws ExecutionException, InterruptedException {
        for (int i = 0; i < 3; i++) {
            ScheduledFuture<String> future = executorService.schedule(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println(Thread.currentThread().getName() + "--flag = " + Thread.currentThread().getId());
                    return Thread.currentThread().getName();
                }
            }, 5, TimeUnit.SECONDS);
            /**
             * future是一个阻塞函数，当调用get方法时，当任务没有执行完成后，会一直阻塞当前线程
             */
            if (i == 1) {
                future.cancel(true);
            } else {
                System.out.println(future.get());
            }
            System.out.println("----------" + "循环" + "------------");
        }
    }
}
