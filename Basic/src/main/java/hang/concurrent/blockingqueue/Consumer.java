package hang.concurrent.blockingqueue;

import java.util.concurrent.BlockingQueue;

/**
 * @author ZhangHang
 * @create 2018-05-07 17:34
 **/
public class Consumer extends Thread{
    private BlockingQueue blockingQueue;

    public Consumer(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        System.out.println("Thread id:"+Thread.currentThread().getId()+"    开始消费Product!");
        //如果没有，则会一直阻塞
        try {
            long product= (Long) blockingQueue.take();
            System.out.println(product);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
