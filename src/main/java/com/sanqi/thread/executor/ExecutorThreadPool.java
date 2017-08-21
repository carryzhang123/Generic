package com.sanqi.thread.executor;

import java.util.concurrent.*;

/**
 * @author ZhangHang
 * @create 2017-08-21 10:58
 **/
public class ExecutorThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorThreadPool executorThreadPool = new ExecutorThreadPool();
        //SingleThreadPool可以创建只有一个线程的线程池，使用的无界的队列
        ExecutorService singleService = Executors.newSingleThreadExecutor();
        //FixedThreadPool可以创建一定数量的线程去执行任务，使用的是无界的队列
        ExecutorService fixedService = Executors.newFixedThreadPool(2);
        //CachedThreadPool使用的是无容量的队列，可以一直创建线程，直至cpu使用完
        ExecutorService cachedService = Executors.newCachedThreadPool();

        executorThreadPool.poolCall(fixedService);
    }

    public void poolRun(ExecutorService executors) {
        for (int i = 0; i < 4; i++) {
            final int flag = i;
            executors.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 3; j++) {
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "--flag = " + flag + " " + " ---loop = : " + j);
                    }
                }
            });
        }
    }

    public void poolCall(ExecutorService executors) throws ExecutionException, InterruptedException {
        for (int i = 0; i < 4; i++) {
            final int flag = i;
            /**
             * Callable接口有返回值，当使用ExecutorService时，必须使用submit进行提交，以获得返回值
             */
            Future<String> future = executors.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    for (int j = 0; j < 2; j++) {
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "--flag = " + flag + " " + " ---loop = : " + j);
                    }
                    return Thread.currentThread().getName();
                }
            });
            if(flag==2){
                //取消当前任务
                future.cancel(true);
            }else {
                System.out.println(future.get());
            }
            System.out.println("---------------------------");
        }
    }
}
