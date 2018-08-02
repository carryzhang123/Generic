package hang.thread.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ZhangHang
 * @create 2017-07-10 17:28
 * 1、ThreadLocal中有个静态内部类ThreadLocalMap，Key：ThreadLocal对象，Value：Object
 * 2、Thread中有个对象threadLocals，为ThreadLocal中ThreadLocalMap类型
 * 3、每个Thread对象的threadLocals对象会保存多个key-value值，对应不同的ThreadLocal对象，所以每个线程才能有独立的数据进行保存
 **/
public class ThreadLocalRun {
    ExecutorService service = Executors.newFixedThreadPool(2);
    ThreadLocal<Long> local = new ThreadLocal<Long>();

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalRun run = new ThreadLocalRun();

        for (int i = 0; i < 2; i++) {
            run.service.execute(new Runnable() {
                @Override
                public void run() {
                    run.local.set(Thread.currentThread().getId());
                }
            });
        }

        Thread.sleep(1000);

        for (int i = 0; i < 2; i++) {
            run.service.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(run.local.get());
                }
            });
        }

        run.service.shutdown();
    }
}
