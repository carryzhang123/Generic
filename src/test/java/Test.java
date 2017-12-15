import java.util.Arrays;

/**
 * @author ZhangHang
 * @create 2017-12-07 12:19
 **/
public class Test {
    public static void main(String[] args) {
        int[] nums = new int[]{5, 3, 9, 4, 1, 2, 12, 19, 15, 6, 16, 8, 11, 17, 7, 10, 14, 13, 18};
        int length = nums.length;
        /**
         * 冒泡排序
         */
//        for(int i=1;i<length;i++){
//            for(int j=0;j<length-i;j++){
//                if(nums[j]>nums[j+1]){
//                    int tmp=nums[j];
//                    nums[j]=nums[j+1];
//                    nums[j+1]=tmp;
//                }
//            }
//        }


        /**
         * shell排序，增量要一直减到1，下标之差等于增量值
         */
//        for (int shell = length / 2; shell >= 1; shell--) {
//            for (int i = 0; i < shell; i++) {
//                for (int j = i; j < length - shell; j += shell) {
//                    if (nums[j] > nums[j + shell]) {
//                        int tmp = nums[j];
//                        nums[j] = nums[j + shell];
//                        nums[j + shell] = tmp;
//                    }
//                }
//            }
//
//        }


        /**
         * 插入排序
         */
//        for (int i = 1; i < length; i++) {
//            int tmp = nums[i];
//            for (int j = i - 1; j >= 0; j--) {
//                if (tmp >= nums[j]) {
//                    break;
//                }
//                nums[j + 1] = nums[j];
//                nums[j] = tmp;
//            }
//        }


        /**
         * 选择排序
         */
//        for (int i = 0; i < length - 1; i++) {
//            int min = i;
//            for (int j = i + 1; j < length; j++) {
//                if (nums[j] < nums[min])
//                    min = j;
//            }
//            int tmp = nums[i];
//            nums[i] = nums[min];
//            nums[min] = tmp;
//        }

        /**
         * 快速排序  先排在分
         */
//        sort(nums, 0, length - 1);
        /**
         * 归并排序  先分在排
         */
//        mergeSort(nums, 0, length - 1);


        System.out.println(Arrays.toString(nums));
    }

    /**
     * 快速排序  递归部分
     */
    public static void sort(int[] nums, int i, int j) {
        if (i < j) {
            int middle = quick(nums, i, j);
            sort(nums, i, middle - 1);
            sort(nums, middle + 1, j);
        }
    }

    /**
     * 快速排序  调整部分
     */
    public static int quick(int[] nums, int i, int j) {
        int tmp = nums[i];
        while (i < j) {
            while (i < j && tmp <= nums[j]) {
                j--;
            }
            nums[i] = nums[j];
            nums[j] = tmp;
            while (i < j && tmp >= nums[i]) {
                i++;
            }
            nums[j] = nums[i];
            nums[i] = tmp;
        }
        return i;
    }

    /**
     * 归并排序  合并
     */
    public static void merge(int[] nums, int low, int middle, int high) {
        int[] newNum = new int[high - low + 1];
        int tmp = 0;
        int i = low, j = middle + 1;
        while (i <= middle && j <= high) {
            if (nums[i] < nums[j]) {
                newNum[tmp++] = nums[i++];
            }
            if (nums[i] >= nums[j]) {
                newNum[tmp++] = nums[j++];
            }
        }

        while (i <= middle) {
            newNum[tmp++] = nums[i++];
        }

        while (j <= high) {
            newNum[tmp++] = nums[j++];
        }

        for (int x = 0; x < newNum.length; x++) {
            nums[low + x] = newNum[x];
        }
    }

    /**
     * 归并排序  分离
     */
    public static void mergeSort(int[] nums, int i, int j) {
        int middle = (j + i) / 2;
        if (i < j) {
            mergeSort(nums, i, middle);
            mergeSort(nums, middle + 1, j);
            merge(nums, i, middle, j);
        }
    }


}
