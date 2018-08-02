package hang.concurrent.assist;

import java.util.concurrent.CountDownLatch;

/**
 * @author ZhangHang
 * @create 2018-05-07 14:30
 **/

/**
 * 1、一个线程在指定个数的线程执行完后，在进行执行
 * 2、await()等待
 * 3、countdown，执行一次，数字减1
 */
public class CountDownLatchTest {
    private final static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        Thread1 run1 = new Thread1();
        Thread thread1 = new Thread(run1);

        Thread2 run2 = new Thread2();
        Thread thread2 = new Thread(run2);

        thread1.start();
        thread2.start();
        countDownLatch.await();
        System.out.println("主线程执行完毕！");
    }


    public static class Thread1 implements Runnable {
        @Override
        public void run() {
            System.out.println("线程1开始执行！");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程1执行完毕！");
            countDownLatch.countDown();
        }
    }

    public static class Thread2 implements Runnable {

        @Override
        public void run() {
            System.out.println("线程2开始执行！");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程2执行完毕！");
            countDownLatch.countDown();
        }
    }
}
