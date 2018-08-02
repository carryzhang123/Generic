package com.hang.thread.threadpool;

/**
 * @author ZhangHang
 * @create 2018-06-09 17:36
 **/
public class ThreadMethod {
    private Thread thread;

    /**
     * 1、让调用线程等待一定时长
     * 2、如果无参，则会等的当前线程执行结束，当前线程才会执行
     */
    public void join(long time) throws InterruptedException {
        thread.join();
    }

    /**
     * 1、线程进入阻塞状态
     * 2、线程让出CPU权限
     * 3、线程释放锁
     */
    public void waitTime(long time) throws InterruptedException {
        thread.wait();
    }

    /**
     * 1、可以让处在阻塞的线程抛出异常，并中断此线程；
     * 2、但是不能中断正在运行的线程
     */
    public void interrupt(){
        thread.interrupt();
    }
}
