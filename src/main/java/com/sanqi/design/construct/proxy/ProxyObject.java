package com.sanqi.design.construct.proxy;

/**
 * @author ZhangHang
 * @create 2018-01-04 16:36
 * 代理类，
 **/
public class ProxyObject extends AbstractObject {
    AbstractObject object=new RealObject();
    @Override
    void say() {
        System.out.println("Before !");
        object.say();
        System.out.println("After !");
    }
}
