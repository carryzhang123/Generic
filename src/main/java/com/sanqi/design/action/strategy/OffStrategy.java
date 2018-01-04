package com.sanqi.design.action.strategy;

/**
 * @author ZhangHang
 * @create 2018-01-04 18:40
 **/
public class OffStrategy implements Strategy {
    @Override
    public float calculate(float price) {
        return (float) (price-2.5);
    }
}
