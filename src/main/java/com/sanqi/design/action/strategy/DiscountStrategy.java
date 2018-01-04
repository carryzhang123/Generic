package com.sanqi.design.action.strategy;

/**
 * @author ZhangHang
 * @create 2018-01-04 18:39
 **/
public class DiscountStrategy implements Strategy {
    @Override
    public float calculate(float price) {
        return (float) (price * 0.5);
    }
}
