package hang.concurrent.blockingqueue;


import java.util.concurrent.BlockingQueue;

/**
 * @author ZhangHang
 * @create 2018-05-07 17:25
 **/

/**
 * 1、需要指定队列大小
 * 2、poll和take方法
 */
public class ArrayBlockingQueueTest {
    private static BlockingQueue<Integer> blockingQueue = new java.util.concurrent.ArrayBlockingQueue<Integer>(5);

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            new Product(blockingQueue).start();
            new Consumer(blockingQueue).start();
        }
    }
}
