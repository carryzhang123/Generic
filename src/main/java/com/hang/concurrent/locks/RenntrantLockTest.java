package com.hang.concurrent.locks;

import org.junit.Test;

/**
 * @author ZhangHang
 * @create 2018-05-03 16:23
 **/
public class RenntrantLockTest {

    public static void main(String[] args) {
        ReentrantLockRun reentrantLockRun = new ReentrantLockRun();

        ReentrantLockRun.Thread1 reen1 = reentrantLockRun.new Thread1();
        Thread thread1 = new Thread(reen1);
        thread1.start();

        ReentrantLockRun.Thread2 reen2 = reentrantLockRun.new Thread2();
        Thread thread2 = new Thread(reen2);
        thread2.start();
    }
}
