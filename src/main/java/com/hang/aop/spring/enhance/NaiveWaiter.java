package com.hang.aop.spring.enhance;

/**
 * @author ZhangHang
 * @create 2017-07-11 21:10
 **/
public class NaiveWaiter implements Waiter {

    @Override
    public void greetTo(String name) {
        System.out.println("greet to " + name + "...");
    }

    @Override
    public void serveTo(String name) {
        System.out.println("serving " + name + "...");
    }
}
