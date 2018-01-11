package com.hang.design.construct.adapt.objectadapt;

/**
 * @author ZhangHang
 * @create 2018-01-03 22:54
 **/
public class Client {
    public static void main(String[] args) {
        AdaptSecond second=new AdaptSecond(new AdaptFirst());
        second.say();
        second.tell();
    }
}
