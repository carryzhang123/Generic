package com.sanqi.design.construct.Adapt.objectadapt;

/**
 * @author ZhangHang
 * @create 2018-01-03 22:53
 * 通过源类的对象实现相同的方法，然后实现Target接口方法
 * 优点：可以通过多个对象包括父类和实现父类的子类来实现相应的方法；便于耦合；缺点：不宜重写父类的方法；
 **/
public class AdaptSecond extends AdaptFirst implements Target {
    private AdaptFirst adaptFirst;

    public AdaptSecond(AdaptFirst adaptFirst) {
        this.adaptFirst = adaptFirst;
    }

    @Override
    public void say() {
        this.adaptFirst.say();
    }

    @Override
    public void tell() {
        System.out.println("Hello ! This is AdaptSecond.");
    }
}
