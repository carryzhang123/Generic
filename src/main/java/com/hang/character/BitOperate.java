package com.hang.character;

import org.junit.Test;

/**
 * @author ZhangHang
 * @create 2018-01-10 17:49
 * 位运算：原码、反码、补码
 * 原码取反是反码，反码加1是补码
 **/
public class BitOperate {

    public static void main(String[] args) {

    }

    /**
     * ＆运算  ：对应位都为1，则结果为1
     */
    @Test
    public void sampleOne() {
        int a = 11;
        System.out.println(a + "   " + Integer.toBinaryString(a));
        int b = 12;
        System.out.println(b + "   " + Integer.toBinaryString(b));
        System.out.println(a + b + "   " + Integer.toBinaryString(a & b));
    }

    /**
     * | 运算：对应位都为0，则结果位0
     */
    @Test
    public void sampleTwo() {
        int a = 10;
        System.out.println(a + "   " + Integer.toBinaryString(a));
        int b = 12;
        System.out.println(b + "   " + Integer.toBinaryString(b));
        System.out.println(a + b + "   " + Integer.toBinaryString(a | b));
    }

    /**
     * ^ 运算：对应位都相同，则结果位0
     */
    @Test
    public void sampleThree() {
        int a = 10;
        System.out.println(a + "   " + Integer.toBinaryString(a));
        int b = 12;
        System.out.println(b + "   " + Integer.toBinaryString(b));
        System.out.println(a + b + "   " + Integer.toBinaryString(a ^ b));
    }

    /**
     * ~ 运算：反转，0变为1，1变为0
     */
    @Test
    public void sampleFour() {
        int a = 10;
        System.out.println(a + "   " + Integer.toBinaryString(a));
        System.out.println(~a + "   " + Integer.toBinaryString(~(a)));
    }

    /**
     * << 运算：向左移动n位，空缺补0
     */
    @Test
    public void sampleFive() {
        int a = -10;
        System.out.println(a + "   " + Integer.toBinaryString(a));
        System.out.println((a << 2) + "   " + Integer.toBinaryString(a << 2));
    }

    /**
     * >> 运算，向右移动n位，带符号，正数补0，负数补1
     */
    @Test
    public void sampleSix() {
        int a = -10;
        System.out.println(a + "   " + Integer.toBinaryString(a));
        System.out.println((a >> 2) + "   " + Integer.toBinaryString(a >> 2));

    }

    /**
     * >>> 运算，向右移动n位，高位都补0
     */
    @Test
    public void sampleSeven() {
        int a = -10;
        System.out.println(a + "   " + Integer.toBinaryString(a));
        System.out.println((a >>> 2) + "   " + Integer.toBinaryString(a >> 2));
    }

    /**
     * 判断int变量a是奇数还是偶数
     */
    @Test
    public void judge() {
        int a = 12;
        if ((a & 1) == 0) {
            System.out.println(a + "   是偶数");
        }
        if ((a & 1) == 1) {
            System.out.println(a + "   是奇数");
        }
    }

    /**
     * 取出int变量a，二进制的第k位，即右移k-1位
     */
    @Test
    public void fetchOrderK() {
        int a = 32123;
        //取出a的第四位   4-1=3
        int k = 3;
        System.out.println(Integer.toBinaryString(a));
        System.out.println((a >> k) & 1);
    }

    /**
     * 将int变量a的第k位设置为0
     */
    @Test
    public void fetchOrderZero() {
        int a = 32123;
        //取出a的第四位   4-1=3
        int k = 3;
        System.out.println(Integer.toBinaryString(a));
        int b = a & ~(1 << k);
        System.out.println(Integer.toBinaryString(b));
    }

    /**
     * 将int变量a的第k位设置为1
     */
    @Test
    public void fetchOrderO() {
        int a = 32123;
        //取出a的第四位   4-1=3
        int k = 2;
        System.out.println(Integer.toBinaryString(a));
        int b = a | (1 << k);
        System.out.println(Integer.toBinaryString(b));
    }


    /**
     * 取两个整数的平均值，防止两个整数相加的和溢出
     */
    @Test
    public void fetchAverage() {
        int a = 32123;
        int b = 21341;
        int c = (a & b) + ((a ^ b) >> 1);
        System.out.println(c);
    }

    /**
     * 判断一个数是不是2的幂
     */
    @Test
    public void justify2() {
        int a = 32;
        boolean flag = ((a & (a - 1)) == 0) && (a != 0);
        if (flag) {
            System.out.println(a + "  是2的幂");
        } else {
            System.out.println(a + "  不是2的幂");
        }
    }

    /**
     * 交换两个数的值
     */
    @Test
    public void swapNums() {
        int a = 11;
        int b = 22;
        a ^= b;
        b ^= a;
        a ^= b;
        System.out.println(a + "  " + b);
    }

    /**
     * 计算绝对值
     */
    @Test
    public void calculateAbs() {
        int a = -1002;
        int b = a >> 31;
        System.out.println((a ^ b) - b);
    }
}
