package com.hang.tools.character;

import org.junit.Test;

import java.util.Arrays;
import java.util.BitSet;

/**
 * @author ZhangHang
 * @create 2018-01-12 11:50
 **/
public class BitSetOperate {

    /**
     * 求一个字符串包含的char
     */
    @Test
    public void containChars() {
        String str = "How do you do? 你好呀";
        BitSet bitSet = new BitSet();
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            bitSet.set(str.charAt(i)); // set bit for char
        }

        StringBuilder sb = new StringBuilder();
        int size = bitSet.size();
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            if (bitSet.get(i)) {
                sb.append((char) i).append(" ");
            }
        }
        System.out.println(sb.toString());
    }

    /**
     * 求素数 有无限个。一个大于1的自然数，如果除了1和它本身外，不能被其他自然数整除(除0以外）的数称之为素数(质数） 否则称为合数
     */
    @Test
    public void computePrime() {
        BitSet sieve = new BitSet(1024);
        int size = sieve.size();
        for (int i = 2; i < size; i++)
            sieve.set(i);
        int finalBit = (int) Math.sqrt(sieve.size());

        for (int i = 2; i < finalBit; i++)
            if (sieve.get(i))
                for (int j = 2 * i; j < size; j += i)
                    sieve.clear(j);

        int counter = 0;
        for (int i = 1; i < size; i++) {
            if (sieve.get(i)) {
                System.out.printf("%5d", i);
                if (++counter % 15 == 0)
                    System.out.println();
            }
        }
        System.out.println();
    }

    /**
     * 进行数字排序
     */
    @Test
    public void sortArray() {
        int[] array = new int[]{423, 700, 9999, 2323, 356, 6400, 1, 2, 3, 2, 2, 2, 2};
        BitSet bitSet = new BitSet(2 << 13);
        // 虽然可以自动扩容，但尽量在构造时指定估算大小,默认为64
        System.out.println("BitSet size: " + bitSet.size());

        for (int i = 0; i < array.length; i++) {
            bitSet.set(array[i]);
        }
        //剔除重复数字后的元素个数
        int bitLen = bitSet.cardinality();

        //进行排序，即把bit为true的元素复制到另一个数组
        int[] orderedArray = new int[bitLen];
        int k = 0;
        for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
            orderedArray[k++] = i;
        }

        System.out.println("After ordering: ");
        for (int i = 0; i < bitLen; i++) {
            System.out.print(orderedArray[i] + "\t");
        }

        System.out.println("iterate over the true bits in a BitSet");
        //或直接迭代BitSet中bit为true的元素iterate over the true bits in a BitSet
        for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
            System.out.print(i + "\t");
        }
        System.out.println("---------------------------");
    }

    /**
     * 将BitSet对象转化为ByteArray
     *
     * @param bitSet
     * @return
     */
    public static byte[] bitSet2ByteArray(BitSet bitSet) {
        byte[] bytes = new byte[bitSet.size() / 8];
        for (int i = 0; i < bitSet.size(); i++) {
            int index = i / 8;
            int offset = 7 - i % 8;
            bytes[index] |= (bitSet.get(i) ? 1 : 0) << offset;
        }
        return bytes;
    }

    /**
     * 将ByteArray对象转化为BitSet
     *
     * @param bytes
     * @return
     */
    public static BitSet byteArray2BitSet(byte[] bytes) {
        BitSet bitSet = new BitSet(bytes.length * 8);
        int index = 0;
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 7; j >= 0; j--) {
                bitSet.set(index++, (bytes[i] & (1 << j)) >> j == 1 ? true
                        : false);
            }
        }
        return bitSet;
    }

    /**
     * 简单使用示例
     */
    public static void simpleExample() {
        String names[] = {"Java", "Source", "and", "Support"};
        BitSet bits = new BitSet();
        for (int i = 0, n = names.length; i < n; i++) {
            if ((names[i].length() % 2) == 0) {
                bits.set(i);
            }
        }

        System.out.println(bits);
        System.out.println("Size : " + bits.size());
        System.out.println("Length: " + bits.length());
        for (int i = 0, n = names.length; i < n; i++) {
            if (!bits.get(i)) {
                System.out.println(names[i] + " is odd");
            }
        }
        BitSet bites = new BitSet();
        bites.set(0);
        bites.set(1);
        bites.set(2);
        bites.set(3);
        bites.andNot(bits);
        System.out.println(bites);
    }

    public static void main(String args[]) {


        //BitSet与Byte数组互转示例
        BitSet bitSet = new BitSet();
        bitSet.set(3, true);
        bitSet.set(98, true);
        System.out.println(bitSet.size() + "," + bitSet.cardinality());
        //将BitSet对象转成byte数组
        byte[] bytes = BitSetOperate.bitSet2ByteArray(bitSet);
        System.out.println(Arrays.toString(bytes));

        //在将byte数组转回来
        bitSet = BitSetOperate.byteArray2BitSet(bytes);
        System.out.println(bitSet.size() + "," + bitSet.cardinality());
        System.out.println(bitSet.get(3));
        System.out.println(bitSet.get(98));
        for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
            System.out.print(i + "\t");
        }
    }


    /**
     * BitSet可以作为一个boolean类型的数组使用，本质是long数组，一个long有4个字节，64个位，每个位只能是0 false或1 true；
     * BitSet初始化每个位都是0，且根据传入数据的hashcode确定那个数组和在数组中的下标；
     * int类型的hashcode为自身，long数组的个数根据传入的最大hashcode>>6的值，即64>>6==1;
     */
    @Test
    public void BitSetCalculate() {
        BitSet bits1 = new BitSet(16);
        BitSet bits2 = new BitSet(16);

        // set some bits
        for (int i = 0; i < 16; i++) {
            if ((i % 2) == 0) {
                bits1.set(i);
            }
            if ((i % 5) != 0) bits2.set(i);
        }
        System.out.println("Initial pattern in bits1: ");
        System.out.println(bits1);
        System.out.println("\nInitial pattern in bits2: ");
        System.out.println(bits2);

        // AND bits
        bits2.and(bits1);
        System.out.println("\nbits2 AND bits1: ");
        System.out.println(bits2);

        // OR bits
        bits2.or(bits1);
        System.out.println("\nbits2 OR bits1: ");
        System.out.println(bits2);

        // XOR bits
        bits2.xor(bits1);
        System.out.println("\nbits2 XOR bits1: ");
        System.out.println(bits2);
    }
}
