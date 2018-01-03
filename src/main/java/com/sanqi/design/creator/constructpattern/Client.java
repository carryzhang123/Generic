package com.sanqi.design.creator.constructpattern;

/**
 * @author ZhangHang
 * @create 2018-01-03 16:34
 * 首先部件构造Build和产品Product之间的关系，即定义Product的结构，然后创建一种或多种的部件构造，去完成这个产品；
 * 其次是Director的作用，主要是规划如何使用部件去完成整个整体，形成一个整的Product;
 **/
public class Client {
    public static void main(String[] args) {
        Build build=new BuildProduct();
        Director director=new Director(build);
        director.construct("part1","part2");
        Product product=director.getProduct();
        System.out.println("part1："+product.getPart1()+"----"+"part2："+product.getPart2());
    }
}
