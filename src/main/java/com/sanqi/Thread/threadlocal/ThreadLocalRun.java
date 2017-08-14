package com.sanqi.Thread.threadlocal;

/**
 * @author ZhangHang
 * @create 2017-07-10 17:28
 * ThreadLocal在set()和get()方法中，第一步根据当前线程的对象，取出该线程Tread对象中的TreadLocalMap变量，该变量类型是TreadLocal类中的
 * ThreadLocalMap静态内部类，该静态内部类中还有一个静态内部类Entry类，且构造函数中需要传入ThreadLocal对象和Object对象，类似于key-value
 * 结构，在set方法中，会不断的创建Entry数组去保存，则get的时候，根据当前的TreadLocal对象，取出相对应的Entry数组中的值
 **/
public class ThreadLocalRun {
    ThreadLocal<Long> longLocal=new ThreadLocal<Long>();
    ThreadLocal<String> strLocal=new ThreadLocal<String>();

    public void set(){
        longLocal.set(Thread.currentThread().getId());
        strLocal.set(Thread.currentThread().getName());
    }

    public Long getLong(){
        return longLocal.get();
    }

    public String getStr(){
        return strLocal.get();
    }

    public static void main(String[] args) throws InterruptedException {
        final ThreadLocalRun test1=new ThreadLocalRun();

        test1.set();
        System.out.println(test1.getLong());
        System.out.println(test1.getStr());

        Thread thread=new Thread(){
            @Override
            public void run() {
                test1.set();
                System.out.println(test1.getLong());
                System.out.println(test1.getStr());
            }
        };

        thread.start();//启动thread
        thread.join();//邀请thread先启动，本线程暂停
    }
}
