package com.hang.design.construct.adapt.classadapt;

/**
 * @author ZhangHang
 * @create 2018-01-03 22:53
 * 通过源类和Target，即继承原有方法，又实现了自己的方法
 * 优点：可以适当重新定义父类的方法；缺点：无法使用继承了父类的其它子类方法，不便于耦合；
 **/
public class AdaptSecond extends AdaptFirst implements Target {
    @Override
    public void tell() {
        System.out.println("Hello ! This is AdaptSecond.");
    }
}
