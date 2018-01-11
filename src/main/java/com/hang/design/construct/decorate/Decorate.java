package com.hang.design.construct.decorate;

/**
 * @author ZhangHang
 * @create 2018-01-04 15:33
 * 装饰角色
 **/
public class Decorate implements Component {
    private Component component;

    public Decorate(Component component) {
        this.component = component;
    }

    @Override
    public void sampleOperation() {
        //委托给构件去操作
        component.sampleOperation();
    }
}
