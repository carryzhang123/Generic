package com.hang.thread.threadpool;

import java.util.concurrent.*;

/**
 * @author ZhangHang
 * @create 2018-06-09 16:27
 **/

/**
 * 1、coorPoolSize：核心线程池大小，默认情况下，线程池初始没有任何线程，除非调用prestart..两个方法，可以创建一个或全部核心线程；
 * 2、maximumPoolSize：线程池最大线程数，表示最多可以创建多少个线程，当有任务时，先创建核心线程去处理，如果核心线程都在运行，则
 *   会去创建额外的线程，最大线程数不能超过这个值，且这些额外的线程如果空闲时间超过keepAliveTime，则会关闭掉；
 * 3、keepAliveTime：表示那些额外线程没有任务执行时最多保持多久时间终止，如果调用allowCoreThreadTimeOut(true)，则对线程池中的任何线程都起作用；
 * 4、unit：参数keepAliveTime的时间单位；
 * 5、workQueue：一个阻塞队列，用来存储等待执行的任务。ArrayBlockingQueue这个是数组形式的任务需要指定大小（不常用）；LinkedBlockingQueue这个
 * 是链表形式的队列，可以指定大小，若不指定则为Integer.MAX_VALUE；SynchronousQueue这个不会缓存任务，若有任务则立刻创建一个新的线程去执行；
 * 6、handler：拒绝处理任务时的策略，即超出maximumPoolSize时。AbortPlicy丢弃任务并抛出异常；DiscardPolicy丢弃任务但不抛出异常；
 * DiscardOldestPolicy丢弃队列最前面的任务直至该任务添加进去；CllerRunsPolicya由当前调用线程处理该任务；
 * 7、execute()：调用此方法向线程池提交一个任务，交给线程池去执行；
 * 8、submit()：也是向线程池提交任务，且可以返回任务执行结果；
 * 9、shutdown()：线程池处于SHUTDOWN状态，此时线程池不能接受新的任务，它会等待所有任务完毕；
 * 10、shutdownnow()：线程池处于STOP状态，不能接受新的任务，并且会去尝试终止正在执行的任务；当线程池处于SHUTDOWN或STOP状态，并且所有工作线程
 *    已经销毁，任务缓存队列已经清空或者执行结束，线程池被设置为TERMINATED状态；
 * 11、线程池中如果有线程抛出异常导致线程停止，则会创造新的线程代替，但原来线程里剩下的任务也不会执行，所以一般都是在子线程业务中处理异常；
 */
public class ThreadPool {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(5));

    /**
     * 创建ExecutorSingle模式，默认AbortPolicy模式
     */
    public void executorSingle() {
        executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }

    /**
     * 创建ExecutorFixed模式，默认AbortPolicy模式
     *
     * @param nThreads
     */
    public void executorFixed(int nThreads) {
        executor = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }

    /**
     * 创建ExecutorCache模式，默认AbortPolicy模式
     */
    public void executorCached() {
        executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>());
    }
}
