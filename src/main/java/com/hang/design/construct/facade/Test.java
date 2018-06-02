package com.hang.design.construct.facade;

/**
 * @author ZhangHang
 * @create 2018-05-29 1:24
 **/

/**
 *  1、系统有多个子功能
 *  2、创建一个外观类，把多个子功能统一起来
 */
public class Test {
    public static void main(String[] args) {
        Facade facade=new Facade();
        facade.say();
    }
}
