package com.hang.concurrent.assist;

/**
 * @author ZhangHang
 * @create 2018-05-07 16:38
 **/

import java.util.concurrent.Semaphore;

/**
 * 1、类似于ReentranLock，表示资源可以被多少个线程使用
 * 2、acquire()获得一个许可
 * 3、release()释放一个许可
 */
public class SemaphoreTest {
    private final static int N = 3;
    private static Semaphore semaphore = new Semaphore(N);

    public static void main(String[] args) {
        for (int i = 0; i < 6; i++) {
            new ThreadRun(semaphore).start();
        }
    }

    public static class ThreadRun extends Thread {
        private Semaphore semaphore;

        public ThreadRun(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("Thread ID:" + Thread.currentThread().getId() + "正在执行资源！");
                Thread.sleep(2000);
                System.out.println("Thread ID:" + Thread.currentThread().getId() + "执行完毕！");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
