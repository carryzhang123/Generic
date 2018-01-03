package com.sanqi.design.creator.factory;

/**
 * @author ZhangHang
 * @create 2018-01-03 17:24
 **/
public class AMD {
    Board board = new Board();
    Cpu cpu = new Cpu();

    public AMD(int para1,int para2) {
        board.setType("AMD");
        board.setParameter(para1);
        cpu.setType("AMD");
        cpu.setParameter(para2);
    }

    public void calculate() {
        System.out.println("CPU  Type:" + cpu.getType() + "----  parameter:" + cpu.getParameter());
        System.out.println("Board  Type:" + board.getType() + "----  parameter:" + board.getParameter());
    }
}
