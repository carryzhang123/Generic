package com.hang.design.construct.decorate;

/**
 * @author ZhangHang
 * @create 2018-01-04 15:35
 * 具体装饰角色
 **/
public class FishDecorate extends Decorate {

    public FishDecorate(Component component) {
        super(component);
    }

    @Override
    public void sampleOperation() {
        super.sampleOperation();
        System.out.println("I am a fish ! I can swim!");
    }
}
