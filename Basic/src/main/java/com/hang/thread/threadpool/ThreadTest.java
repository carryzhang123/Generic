package com.hang.thread.threadpool;

import java.util.concurrent.*;

/**
 * @author ZhangHang
 * @create 2018-07-04 17:11
 **/
public class ThreadTest {
    public static void main(String[] args) {
        ThreadTest threadTest = new ThreadTest();
        threadTest.interruptPoolTest();
    }

    public void interruptPoolTest() {
        ExecutorService service = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1), new ThreadPoolExecutor.DiscardPolicy());

        for(int i=0;i<10;i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("this is 2");
                }
            });
        }
    }
}
