package com.hang.thread.tiantask;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZhangHang
 * @create 2018-05-02 16:02
 **/
public class Actor {
    private ExecutorService executor;
    //LinkedTransferQueue实现了TransferQueue这个接口，即生产者知道消费者的需求，是一个无边界的Queue
    private final Queue<Runnable> queue = new LinkedTransferQueue<>();
    private final AtomicInteger lock = new AtomicInteger();
    private Actor.Workder workder = new Actor.Workder();

    public Actor(ExecutorService executor) {
        if (executor == null) {
            throw new IllegalArgumentException("executor is null!");
        } else {
            this.executor = executor;
        }
    }

    public boolean dispatch(Runnable task) {
        if (task == null) {
            throw new IllegalArgumentException("task is null!");
        } else {
            this.queue.offer(task);
            //当一次传入多个Task时，可以保证通过执行workder使queue里面的任务在一个线程里顺序执行
            if (this.lock.getAndIncrement() == 0) {
                this.executor.execute(this.workder);
            }
            return true;
        }
    }

    //关闭
    public void shutdown() throws InterruptedException {
        this.executor.shutdown();
        while (!this.executor.isTerminated()) {
            Thread.sleep(100);
        }
    }

    //任务个数
    public int getWaitingSize(){
        return this.queue.size();
    }


    //获取Queue里面的Runable任务
    private Runnable getTask() {
        return this.queue.poll();
    }

    private class Workder implements Runnable {
        @Override
        public void run() {
            for (Runnable work = Actor.this.getTask(); work != null; work = Actor.this.getTask()) {
                try {
                    work.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //如果当先线程里的queue里任务个数全部执行，则清0，下次可以在使用一个线程去执行
                if (Actor.this.lock.decrementAndGet() == 0) {
                    break;
                }
            }
        }
    }
}
