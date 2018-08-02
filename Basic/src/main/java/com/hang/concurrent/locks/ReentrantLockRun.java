package com.hang.concurrent.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhangHang
 * @create 2018-05-03 15:47
 **/

/**
 * ReentrantLock是根据AQS中独占锁实现的形式
 * 1、获取锁和释放锁是对等的，没一次获取都必然对应一次释放，即使是同一个线程重复获取，也对应着重复释放
 * 2、获取锁->当前资源状态->若是0，设置当前独占线程为当前线程，1的话，acquire()方法->tryAcquire()成功直接返回->失败，加入队列的尾部，标记独占模式
 * ->将自己找到最近没放弃线程的后面，即前个线程为SIGN状态->等待unpark()被唤醒->调整队列，获取资源（如果被中断过，则在下次启动后才会知道）
 * 3、释放锁->tryRelease()->状态设置0->unparkSuccessor()唤醒下一个等待者
 */
class ReentrantLockRun {
    private Lock lock = new ReentrantLock();
    private int i = 0;

    private void increase() {
        lock.lock();
        try {
            int tmp = i + 1;
            Thread.sleep(1000);
            i = tmp;
            System.out.println("Thread Id:" + Thread.currentThread().getId() + "    " + " i:" + i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public class Thread1 implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 1; i++) {
                increase();
            }
        }
    }

    public class Thread2 implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 1; i++) {
                increase();
            }
        }
    }
}
