package hang.concurrent.locks;

/**
 * @author ZhangHang
 * @create 2018-05-03 21:23
 **/
public class ReentrantReadWriteTest {
    public static void main(String[] args) {
        ReentrantReadWriteRun reentrantReadWriteRun=new ReentrantReadWriteRun();
        ReentrantReadWriteRun.data.put(1,1);

        ReentrantReadWriteRun.Thread2 run2=reentrantReadWriteRun.new Thread2(2,2);
        Thread thread2=new Thread(run2);
        thread2.start();

        ReentrantReadWriteRun.Thread1 run1=reentrantReadWriteRun.new Thread1(2,2);
        Thread thread1=new Thread(run1);
        thread1.start();
    }
}
