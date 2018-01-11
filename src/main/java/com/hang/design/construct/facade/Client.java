package com.hang.design.construct.facade;

/**
 * @author ZhangHang
 * @create 2018-01-04 18:27
 * 系统有多个子功能，则设计一个外观类，将多个子功能集成到里面，客户端直接调用外观类即可；
 **/
public class Client {
    public static void main(String[] args) {
        Facade facade=new Facade();
        facade.say();
    }
}
