package com.hang.design.action.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangHang
 * @create 2018-01-04 17:34
 **/
public class Subject {
    //存放观察者对象
    private List<Observer> list = new ArrayList<Observer>();

    //增加要通知的观察者对象
    public void attachSubject(Observer observer) {
        list.add(observer);
    }

    //移除无用的观察者
    public void deleteSubject(Observer observer) {
        list.remove(observer);
    }

    //通知观察者主题中所改变的对象
    public void updateStatus(int number) {
        for (Observer observer : list) {
            observer.update(number);
        }
    }
}
