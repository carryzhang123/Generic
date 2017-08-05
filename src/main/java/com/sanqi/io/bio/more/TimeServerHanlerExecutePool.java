package com.sanqi.io.bio.more;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangHang
 * @create 2017-07-20 14:45
 **/
public class TimeServerHanlerExecutePool {
    private ExecutorService executor;

    public TimeServerHanlerExecutePool(int maxPoolSize,int queueSize) {
        /**
         * (核心线程池大小，最大线程池大小，线程池超过coorPoolSize的线程存活最大时间，时间单位，queueSize容量的阻塞队列)
         */
        executor=new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),maxPoolSize,120L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task){
        executor.execute(task);
    }
}
