package com.hang.concurrent.executor;

import java.util.concurrent.*;

/**
 * @author ZhangHang
 * @create 2017-08-21 10:58
 **/

/**
 * 1、创建线程池，可以通过Excutors直接创建，也可以创建自己定义的线程池
 * 2、Future可以获取返回线程的状态，其中future.get()是阻塞的
 * 3、ScheduledExecutorService可以在指定的时间延迟后去执行任务
 */
public class ExecutorThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorThreadPool pool = new ExecutorThreadPool();
        ExecutorService service = pool.fixedScheduleThread();
        pool.forWardScheduleRun((ScheduledExecutorService) service);
        service.shutdown();
    }

    public void forWardRun(ExecutorService executors) {
        for (int i = 0; i < 10; i++) {
            executors.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread ID:" + Thread.currentThread().getId());
                }
            });
        }
    }

    public void forWardScheduleRun(ScheduledExecutorService executors) {
        for (int i = 0; i < 10; i++) {
            executors.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread ID:" + Thread.currentThread().getId());
                }
            }, 3000, TimeUnit.MILLISECONDS);
        }
    }

    public void callbackRun(ExecutorService executors) throws ExecutionException, InterruptedException {
        Future<Long> future = null;
        for (int i = 0; i < 10; i++) {
            future = executors.submit(new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    System.out.println("innerior");
                    Thread.sleep(3000);
                    return Thread.currentThread().getId();
                }
            });
            System.out.println("exterior");
            System.out.println("Thrad Id:" + future.get());
        }
    }

    /**
     * Excutors创建单个线程池
     */
    private ExecutorService singleThread() {
        return Executors.newSingleThreadExecutor();
    }

    /**
     * Excutors创建一定数量的线程池
     */
    private ExecutorService fixedThread() {
        return Executors.newFixedThreadPool(6);
    }

    /**
     * Excutors创建无限的线程池
     */
    private ExecutorService infiniteThread() {
        return Executors.newCachedThreadPool();
    }

    /**
     * Excutors创建schedule线程
     */
    private ScheduledExecutorService fixedScheduleThread() {
        return Executors.newScheduledThreadPool(6);
    }

    /**
     * 自定义线程池
     */
    private ThreadPoolExecutor flexibleThread() {
        int corePoolSize = 5;//核心线程数
        int maxmunPoolSize = 10;//最大线程数
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(20);//创建等待队列
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxmunPoolSize, 2, TimeUnit.SECONDS, queue);
        return executor;
    }
}
