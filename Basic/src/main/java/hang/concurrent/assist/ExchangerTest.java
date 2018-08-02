package hang.concurrent.assist;

/**
 * @author ZhangHang
 * @create 2018-05-07 17:49
 **/

import java.util.concurrent.Exchanger;

/**
 * 1、主要作为两个线程之间交换数据
 * 2、只有连个线程都到达交换点，才进行交换，否则阻塞
 * 3、exchange()方法会交换数据，同时得到对方的数据
 */
public class ExchangerTest {
    private static Exchanger<Long> exchanger=new Exchanger<>();

    public static void main(String[] args) {
        new ThreadRun(exchanger).start();
        new ThreadRun(exchanger).start();
    }

    public static class ThreadRun extends Thread {
        private Exchanger exchanger;

        public ThreadRun(Exchanger exchanger) {
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            System.out.println("到达交换点：" + Thread.currentThread().getId());
            try {
                long id = Thread.currentThread().getId();
                System.out.println("开始交换："+id+"-----"+exchanger.exchange(id));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
