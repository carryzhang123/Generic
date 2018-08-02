package com.hang.design.creator.factory;

/**
 * @author ZhangHang
 * @create 2018-01-03 17:35
 **/
public class Intel {
    Board board = new Board();
    Cpu cpu = new Cpu();

    public Intel(int para1, int para2) {
        board.setType("Intel");
        board.setParameter(para1);
        cpu.setType("Intel");
        cpu.setParameter(para2);
    }

    public void calculate() {
        System.out.println("CPU  Type:" + cpu.getType() + "----  parameter:" + cpu.getParameter());
        System.out.println("Board  Type:" + board.getType() + "----  parameter:" + board.getParameter());
    }
}
