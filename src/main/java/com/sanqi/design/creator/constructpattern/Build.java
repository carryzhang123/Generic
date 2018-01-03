package com.sanqi.design.creator.constructpattern;

/**
 * @author ZhangHang
 * @create 2018-01-03 16:25
 * 创建产品
 **/
public interface Build {
    void buildPart1(String part1);

    void buildPart2(String part2);

    Product getProduct();
}
