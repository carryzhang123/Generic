package com.sanqi.design.action.strategy;

/**
 * @author ZhangHang
 * @create 2018-01-04 18:41
 * 通过一个策略接口，实现不同的策略方法；
 * 环境Price，通过传入的参数和策略去执行；
 * 客户端往环境中传入相应的数据和策略得到不同的结果；
 **/
public class Client {
    public static void main(String[] args) {
        OffStrategy off=new OffStrategy();
        DiscountStrategy discount=new DiscountStrategy();
        Price price=new Price();
        //使用不同的策略
        price.calculate(off, (float) 5.5);
        price.calculate(discount, (float) 5.5);
    }
}
