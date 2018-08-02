package hang.arithmetic;

import java.util.Arrays;
import java.util.Random;

/**
 * @author ZhangHang
 * @create 2017-08-16 12:18
 * 蓄水池算法：
 * 对于长度未知的数组中选出n个数据，且保证选出的每个数据的概率相同
 **/
public class Reservior {
    public static void main(String[] args) {
        int k = 15;
        int n = 1000;
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = i;
        }
        int[] sample = reservoirSampling(data, k);
        System.out.println(Arrays.toString(sample));
    }

    /**
     * n个数据中选出k个数据：
     * 1、建立一个k长度的数组，把n中的前k个数据放入数组中，则每个数据的概率都为1/k
     * 2、从1-m中随机取值，k<m<=n，如果取出的值j下标小于或等于k，则把第j个数据和第m个数据进行交换
     * @param data
     * @param k
     * @return
     */
    public static int[] reservoirSampling(int[] data, int k) {
      int[] tmp=new int[k];
      int n=data.length;
      for(int i=0;i<n;i++){
          if(i<k){
              tmp[i]=data[i];
          }else {
              int j=new Random().nextInt(i);
              if(j<k){
                  tmp[j]=data[i];
              }
          }
      }
      return tmp;
}
}