package hang.concurrent.blockingqueue;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author ZhangHang
 * @create 2018-05-07 17:41
 **/

/**
 * 1、可以指定大小
 * 2、不指定大小，则按Integer.MAX_VALUE计算
 */
public class LinkedBlockingQueueTest {
    private static LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            new Product(linkedBlockingQueue).start();
            new Consumer(linkedBlockingQueue).start();
        }
    }
}
