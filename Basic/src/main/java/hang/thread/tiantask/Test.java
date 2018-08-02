package hang.thread.tiantask;

/**
 * @author ZhangHang
 * @create 2018-05-02 21:41
 **/
public class Test {
    public static void main(String[] args) throws InterruptedException {
        TianEvent event1=TianEvent.valueOf(RunnableKey.FIRST.threadId(), new Runnable() {
            @Override
            public void run() {
                System.out.println("this is First threadId:"+Thread.currentThread().getId());
            }
        });

        TianEvent event2=TianEvent.valueOf(RunnableKey.SECOND.threadId(), new Runnable() {
            @Override
            public void run() {
                System.out.println("this is second :"+Thread.currentThread().getId());
            }
        });

        TianEvent event3=TianEvent.valueOf(RunnableKey.SECOND.threadId(), new Runnable() {
            @Override
            public void run() {
                System.out.println("this is Three :"+Thread.currentThread().getId());
            }
        });

        EventBusManager.submit(event1);
        EventBusManager.submit(event2);
        EventBusManager.submit(event3);
    }
}
