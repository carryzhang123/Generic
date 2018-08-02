package com.hang.thread.threadpool;

import com.hang.exception.CastException;

import java.util.concurrent.Executors;

/**
 * @author ZhangHang
 * @create 2018-07-04 17:20
 **/
public class ThreadException implements Runnable{

    @Override
    public void run() {
        System.out.println("begin!");
    }

    public void throwException() throws CastException {
        throw new CastException("thread interrupt!");
    }
}
