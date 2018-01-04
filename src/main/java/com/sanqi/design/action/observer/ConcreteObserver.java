package com.sanqi.design.action.observer;

/**
 * @author ZhangHang
 * @create 2018-01-04 17:36
 **/
public class ConcreteObserver extends Observer {
    private String state;

    @Override
    public void set(String state) {
        this.state = state;
    }

    @Override
    void update(String parameter) {
        this.state = parameter;
        System.out.println("当前观察者的参数已更新：" + state);
    }

    public void say() {
        System.out.println("当前观察者的参数是：" + state);
    }
}
