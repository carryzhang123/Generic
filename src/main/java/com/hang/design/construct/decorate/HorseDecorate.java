package com.hang.design.construct.decorate;

/**
 * @author ZhangHang
 * @create 2018-01-04 16:09
 **/
public class HorseDecorate extends Decorate {
    public HorseDecorate(Component component) {
        super(component);
    }

    @Override
    public void sampleOperation() {
        super.sampleOperation();
        System.out.println("I am a horse ! I can run rapidly!");
    }
}
