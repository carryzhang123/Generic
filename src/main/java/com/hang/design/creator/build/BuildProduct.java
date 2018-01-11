package com.hang.design.creator.build;

/**
 * @author ZhangHang
 * @create 2018-01-03 16:26
 * 创建产品的具体形式
 **/
public class BuildProduct implements Build {
    private Product product = new Product();

    @Override
    public void buildPart1(String part1) {
        product.setPart1(part1);
    }

    @Override
    public void buildPart2(String part2) {
        product.setPart2(part2);
    }

    @Override
    public Product getProduct() {
        return product;
    }
}
