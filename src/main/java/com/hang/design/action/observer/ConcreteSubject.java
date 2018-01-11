package com.hang.design.action.observer;

/**
 * @author ZhangHang
 * @create 2018-01-04 17:41
 **/
public class ConcreteSubject extends Subject {
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void change(String parameter){
        this.subject=parameter;
        System.out.println("主题的状态已经改变为："+subject);
        this.updateStatus(subject);
    }
}
