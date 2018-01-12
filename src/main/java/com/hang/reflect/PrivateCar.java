package com.hang.reflect;

/**
 * @author ZhangHang
 * @create 2017-07-08 15:50
 **/
public class PrivateCar {

    private String color;

    protected void drive(){
        System.out.println("drive private car! the color is :"+color);
    }
}
