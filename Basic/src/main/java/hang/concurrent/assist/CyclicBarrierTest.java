package hang.concurrent.assist;

/**
 * @author ZhangHang
 * @create 2018-05-07 16:05
 **/

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 1、指定N个线程，全部执行完某个任务后，在去执行其它的任务
 * 2、await(time)，当前barrier状态time时间后，则不会继续等待，直接去执行；
 * 3、CyclicBarrier可以重用，指定的几个线程执行过后，继续可以加入别的线程使用
 */
public class CyclicBarrierTest {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

    public static void main(String[] args) throws InterruptedException {
        new ThreadRun(cyclicBarrier,1000,0).start();
        new ThreadRun(cyclicBarrier,1000,0).start();
        new ThreadRun(cyclicBarrier,1000,2000).start();

        Thread.sleep(3000);
        System.out.println("-----------------------------------------------");
        new ThreadRun(cyclicBarrier,1000,0).start();
        new ThreadRun(cyclicBarrier,1000,0).start();
        new ThreadRun(cyclicBarrier,1000,2000).start();
    }


    public static class ThreadRun extends Thread {
        private CyclicBarrier cyclicBarrier;
        private long waitTime;
        private long sleepTime;

        public ThreadRun(CyclicBarrier cyclicBarrier,long waitTime,long sleepTime) {
            this.cyclicBarrier = cyclicBarrier;
            this.waitTime=waitTime;
            this.sleepTime=sleepTime;
        }

        @Override
        public void run() {
            System.out.println("Thread id:" + Thread.currentThread().getId() + "   开始执行任务1！");
            try {
                Thread.sleep(sleepTime);
                cyclicBarrier.await(waitTime, TimeUnit.MILLISECONDS);
            } catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("Thread id:" + Thread.currentThread().getId() + "      任务1全部执行完毕，开始执行任务2！");
        }
    }

}
