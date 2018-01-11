package com.hang.design.action.observer;

/**
 * @author ZhangHang
 * @create 2018-01-04 17:35
 **/
public abstract class Observer {
    abstract void update(String parameter);

    abstract void set(String parameter);

    abstract void say();
}
