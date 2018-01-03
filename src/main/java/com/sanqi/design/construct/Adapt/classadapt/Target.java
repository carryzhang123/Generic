package com.sanqi.design.construct.Adapt.classadapt;

/**
 * @author ZhangHang
 * @create 2018-01-03 22:50
 * 目标文件，将一个类的方法继承下来并添加到另一个类
 **/
public interface Target {
    //这是源类AdaptFirst有的方法
     void say();
     //这是源类AdaptFirst没有的方法
    void tell();
}
