package com.hang.design.creator.build;

/**
 * @author ZhangHang
 * @create 2018-01-03 16:30
 * 通过Build类进行创建
 **/
public class Director {
    private Build build;

    public Director(Build build) {
        this.build = build;
    }

    public void construct(String part1,String part2){
        build.buildPart1(part1);
        build.buildPart2(part2);
    }

    public Product getProduct(){
        return build.getProduct();
    }
}
