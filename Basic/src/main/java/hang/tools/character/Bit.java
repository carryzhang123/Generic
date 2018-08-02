package hang.tools.character;

import org.junit.jupiter.api.Test;

/**
 * @author ZhangHang
 * @create 2018-01-25 14:29
 **/
public class Bit {
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
        a |= (1 << k);
        System.out.println(Integer.toBinaryString(a));
    }
}
