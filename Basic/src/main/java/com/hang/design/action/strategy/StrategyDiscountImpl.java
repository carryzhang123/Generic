package com.hang.design.action.strategy;

/**
 * @author ZhangHang
 * @create 2018-01-04 18:39
 **/
public class StrategyDiscountImpl implements Strategy {
    private int flag;

    public StrategyDiscountImpl(int flag) {
        this.flag = flag;
    }

    @Override
    public float calculate(float price) {
        return (float) (price * 0.5);
    }

    @Override
    public int getFlag() {
        return flag;
    }
}
