package com.hang.concurrent.cas;

/**
 * @author ZhangHang
 * @create 2018-05-02 16:51
 **/
public class CASTest {
    private static CAS cas = new CAS(1);

    public static void main(String[] args) throws InterruptedException {
        Thread1 run1 = new Thread1();
        Thread thread = new Thread(run1);
        thread.start();

        Thread2 run2 = new Thread2();
        Thread thread1 = new Thread(run2);
        thread1.start();

    }

    public static class Thread1 implements Runnable {

        @Override
        public void run() {
            int current = cas.get();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //CAS中的乐观锁形势，如果失败则不断进行比较
            while (!cas.update(current, 3)) {
                current = cas.get();
            }

            System.out.println("update value is:"+cas.get());
        }
    }

    public static class Thread2 implements Runnable {

        @Override
        public void run() {
            int current = cas.get();
            while (!cas.update(current, 5)) {
                current = cas.get();
            }

            System.out.println("update value is:"+cas.get());
        }
    }
}
