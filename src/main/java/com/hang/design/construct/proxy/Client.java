package com.hang.design.construct.proxy;

/**
 * @author ZhangHang
 * @create 2018-01-04 16:37
 * 建立抽象类，然后实现具体的情况；
 * 使用代理类继承抽象类，重写父类方法，并建立另一个子类的对象，则可以在实现另一个子类功能的基础上实现自己的功能，达到代理的效果；
 **/
public class Client {
    public static void main(String[] args) {
        AbstractObject object=new ProxyObject();
        object.say();
    }
}
