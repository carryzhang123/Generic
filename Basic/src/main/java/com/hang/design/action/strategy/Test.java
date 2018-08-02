package com.hang.design.action.strategy;

/**
 * @author ZhangHang
 * @create 2018-05-28 23:34
 **/

/**
 * 1、创建策略接口
 * 2、实现策略接口，创建不同的策略
 * 3、传入不同的策略，得到相应的结果
 */
public class Test {
    public static void main(String[] args) {
        //获取不同的策略
        Strategy discount=new StrategyDiscountImpl(1);
        Strategy substrate=new StrategySubstrateImpl(2);

        Price price=new Price((float) 23.7);

        //传入不同的策略
        price.calculate(discount);
        price.calculate(substrate);
    }
}
