package com.hang.concurrent.locks;

/**
 * @author ZhangHang
 * @create 2018-05-03 21:05
 **/

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * 1、读锁共享，写锁独占
 * 2、读锁被占情况下，不能获取写锁，因为读锁是共享的，可能其它的线程也有读锁
 * 3、本线程持有写锁情况下，可以获取读锁，因为写锁是独占的，且一个线程是顺序执行的，但其它线程获取写锁，本线程不能获取读锁
 * 4、写锁读锁获取时，先释放读锁，即写锁降到读锁
 * 5、读锁不能升到写锁，即不能先读在写
 */
public class ReentrantReadWriteRun {
    public static Map<Integer, Integer> data = new HashMap<Integer, Integer>();
    //非公平锁
    private static ReadWriteLock lock = new ReentrantReadWriteLock(false);
    private static Lock rlock = lock.readLock();
    private static Lock wlock = lock.writeLock();

    /**
     * 读锁，共享，写锁情况下可获取
     *
     * @param key
     * @return
     */
    public static Integer get(Integer key) {
        rlock.lock();
        try {
            return data.get(key);
        } finally {
            rlock.unlock();
        }
    }

    /**
     * 写锁，独占，读锁情况下不能获取
     *
     * @param key
     * @param value
     * @return
     */
    public static Integer put(Integer key, Integer value) {
        wlock.lock();
        try {
            return data.put(key, value);
        } finally {
            wlock.unlock();
        }
    }


    public class Thread1 implements Runnable {
        private int key;
        private int value;

        public Thread1(int key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public void run() {
            put(key, value);
        }
    }

    public class Thread2 implements Runnable {
        private int key;
        private int value;

        public Thread2(int key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public void run() {
            System.out.println("begin get data!");
            System.out.println(get(key));
        }
    }
}
