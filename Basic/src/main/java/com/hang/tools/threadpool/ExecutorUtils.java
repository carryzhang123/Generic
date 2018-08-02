package com.hang.tools.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangHang
 * @create 2018-05-26 17:22
 **/
public class ExecutorUtils {
    private static ExecutorService executorService = Executors.newFixedThreadPool(4);

    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);

    public static void addTask(Runnable runnable) {
        executorService.execute(runnable);
    }

    public static void addTask(Runnable runnable, long time) {
        scheduledExecutorService.schedule(runnable, time, TimeUnit.MILLISECONDS);
    }

    public static void addTask(Runnable runnable, long time, long period) {
        scheduledExecutorService.scheduleAtFixedRate(runnable, time, period, TimeUnit.MILLISECONDS);
    }
}
