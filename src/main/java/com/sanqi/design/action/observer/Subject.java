package com.sanqi.design.action.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangHang
 * @create 2018-01-04 17:34
 * 抽象主题，规定对观察对象的基本操作
 **/
public class Subject {
    private List<Observer> list = new ArrayList<Observer>();

    public void attachSubject(Observer observer) {
        list.add(observer);
        System.out.println("Attach a new Suject!");
    }

    public void deleteSubject(Observer observer) {
        list.remove(observer);
    }

    public void updateStatus(String parameter) {
        for (Observer observer : list) {
            observer.update(parameter);
        }
    }
}
