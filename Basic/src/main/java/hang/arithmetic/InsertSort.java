package hang.arithmetic;

/**
 * @author ZhangHang
 * @create 2017-09-18 18:16
 **/

import java.util.Arrays;

/**
 * 插入排序，前n个元素（n=1,2..n）都是有序的元素，把第n+1个元素，添加到相应的有序位置
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] a = {3,1,5,7,2,4,9,6,10,8};
        InsertSort  obj=new InsertSort();
        System.out.println("初始值：");
        System.out.println(Arrays.toString(a));
        insert(a);
        System.out.println("\n排序后：");
        System.out.println(Arrays.toString(a));
    }

    public static void insert(int[] nums){
        for(int i=1;i<nums.length;i++){
            for(int n=i;n>0;n--){
                int a=nums[n];
                int b=nums[n-1];
                if(a<b){
                    nums[n-1]=a;
                    nums[n]=b;
                }else {
                    break;
                }
            }
        }
    }
}
