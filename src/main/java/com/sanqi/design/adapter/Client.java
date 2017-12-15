package com.sanqi.design.adapter;

/**
 * @author ZhangHang
 * @create 2017-09-16 18:04
 **/

/**
 * 测试类
 */
public class Client {
    public static void main(String[] args) {
        //使用普通功能类
        ConcreteTarget concreteTarget=new ConcreteTarget();
        concreteTarget.request();

        //使用适配类，即特殊功能类
        Adapter adapter=new Adapter();
        adapter.request();
    }
}
