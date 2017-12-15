package com.sanqi.design.adapter;

/**
 * @author ZhangHang
 * @create 2017-09-16 18:02
 **/

/**
 * 适配器类，继承了被适配的类，同时实现了接口
 */
public class Adapter extends Adaptee implements Target{
    @Override
    public void request() {
        super.specificRequest();
    }
}
