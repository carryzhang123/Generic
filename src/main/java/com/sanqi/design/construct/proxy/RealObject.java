package com.sanqi.design.construct.proxy;

/**
 * @author ZhangHang
 * @create 2018-01-04 16:36
 * 具体实现对象
 **/
public class RealObject extends AbstractObject {
    @Override
    void say() {
        System.out.println("I am real object!");
    }
}
