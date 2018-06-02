package com.hang.design.action.strategy;

/**
 * @author ZhangHang
 * @create 2018-01-04 18:41
 **/
public class Price {
    private float price;

    public Price(float price) {
        this.price = price;
    }

    public void calculate(Strategy strategy) {
        System.out.println("策略_" + strategy.getFlag() + ":  " + strategy.calculate(price));
    }
}
