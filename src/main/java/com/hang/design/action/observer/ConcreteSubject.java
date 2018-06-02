package com.hang.design.action.observer;

/**
 * @author ZhangHang
 * @create 2018-01-04 17:41
 **/
public class ConcreteSubject extends Subject {
    private int number;

    public int getNumber() {
        return number;
    }

    public void change(int number) {
        this.number = number;
        //通知观察者
        this.updateStatus(number);
    }
}
