package com.hang.design.action.strategy;

/**
 * @author ZhangHang
 * @create 2018-01-04 18:41
 **/
public class Price {
    public void calculate(Strategy strategy, float price) {
        System.out.println(strategy.calculate(price));
    }
}
