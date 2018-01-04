package com.sanqi.design.action.observer;

/**
 * @author ZhangHang
 * @create 2018-01-04 17:48\
 * 主题：即一般为核心功能，修改会影响很多其它功能；
 * 观察者：当主题修改时，与主题相关的功能也需要修改；
 * 为了降低功能的耦合性，在抽象主题中存放着相关观察者的集合，当主题的状态修改时，会通知每个已添加的观察者；
 * 抽象观察者定义了可以更改自己状态的行为，当主题状态修改时，通知了观察者，即可将主题的状态更新的观察者里；
 **/
public class Client {
    public static void main(String[] args) {
        Observer observer = new ConcreteObserver();
        observer.set("我是观察者");
        observer.say();
        ConcreteSubject subject = new ConcreteSubject();
        subject.attachSubject(observer);
        subject.change("我是新的主题");

    }
}
