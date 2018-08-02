package com.hang.design.construct.decorate;

/**
 * @author ZhangHang
 * @create 2018-05-28 23:55
 **/

/**
 * 1、构件：创建数个基本构件，这样其它装饰对象都可以通过这些基本构件实现基本操作
 * 2、装饰：在基础操作上实现自己独有的操作，其中基本操作通过构件去实现，自己独有操作通过自身去实现
 */
public class Test {
    public static void main(String[] args) {
     Decorate fish=new DecorateFish(new ComponentImpl());
     Decorate horse=new DecorateHorse(new ComponentImpl());

     fish.say();
     horse.say();
    }
}
