package hang.concurrent.blockingqueue;

import java.util.concurrent.BlockingQueue;

/**
 * @author ZhangHang
 * @create 2018-05-07 17:30
 **/
public class Product extends Thread {
    private BlockingQueue blockingQueue;

    public Product(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        long threadId = Thread.currentThread().getId();
        System.out.println("Thread id:" + Thread.currentThread().getId() + "    开始生成Product！");
        //成功 true  失败 false
        blockingQueue.offer(threadId);
    }
}
