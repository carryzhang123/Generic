package com.hang.design.construct.decorate;

/**
 * @author ZhangHang
 * @create 2018-01-04 15:31
 * 具体构件角色
 **/
public class ConcreteComponent implements Component {
    @Override
    public void sampleOperation() {
        System.out.println("I am origin! I can fly!");
    }
}
