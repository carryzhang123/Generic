package com.hang.design.construct.adapt;

import com.hang.design.construct.adapt.impl.AdapteeImpl;

/**
 * @author ZhangHang
 * @create 2018-06-03 19:14
 **/

/**
 * 1、数据源A类，目标类B类，在不改变A、B的前提下，让A的数据在B方法中使用，则可以使用适配器
 * 2、适配器继承A，实现B，在B的实现方法中调用A的数据并编写自己的逻辑；
 */
public class Adapter extends AdapteeImpl implements ITarget{
    @Override
    public void target() {
        System.out.print("I am target ,I want to say:   ");
        super.say();
    }
}
