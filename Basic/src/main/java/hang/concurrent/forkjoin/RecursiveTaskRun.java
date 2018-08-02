package hang.concurrent.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @author ZhangHang
 * @create 2018-05-11 17:12
 **/

/**
 * 1、Fork/Join是一个将大任务拆成多个小任务，然后分给多个线程执行，每个线程执行对应队列的任务，执行完把结果丢个同一个队列中，在启动一个线程去执行；
 * 2、工作窃取算法：某个线程窃取另一个线程队列中的任务执行，线程处理的队列是双端队列，如一个线程已经把本队列的任务执行完了，而另一个线程队列里的任务
 * 没有执行完，这次执行完的线程可以从另一个线程队列尾取出任务去执行；
 * 3、ForkJoinTask：我们要使用ForkJoin框架，必须首先创建一个ForkJoin任务。它提供在任务中执行fork()和join()操作的机制，
 * 通常情况下我们不需要直接继承ForkJoinTask类，而只需要继承它的子类，
 * Fork/Join框架提供了以下两个子类：RecursiveAction：用于没有返回结果的任务。RecursiveTask ：用于有返回结果的任务。
 * 4、ForkJoinPool ：ForkJoinTask需要通过ForkJoinPool来执行，任务分割出的子任务会添加到当前工作线程所维护的双端队列中，进入队列的头部。当一个工作线程的队列里暂时没有任务时，它会随机从其他工作线程的队列的尾部获取一个任务。
 */
public class RecursiveTaskRun extends RecursiveTask {
    private final int THRESHOLD = 2;
    private int start;
    private int end;

    public RecursiveTaskRun(int start, int end) {
        this.start = start;
        this.end = end;
    }

    //任务类
    @Override
    protected Object compute() {
        int sum = 0;

        //是否分割
        boolean isCompute = (end - start) < THRESHOLD;
        if (isCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            int middle = (start + end) / 2;
            //创建分割的子任务
            RecursiveTaskRun leftTask = new RecursiveTaskRun(start, middle);
            RecursiveTaskRun rightTask = new RecursiveTaskRun(middle + 1, end);
            //子任务执行
            leftTask.fork();
            rightTask.fork();
            //子任务都执行完毕，然后在一个线程中对执行完毕的结果进行处理
            int leftResult = (int) leftTask.join();
            int rightResult = (int) rightTask.join();
            sum = leftResult + rightResult;
        }

        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        RecursiveTaskRun recursiveTaskRun = new RecursiveTaskRun(1, 4);
        Future future = forkJoinPool.submit(recursiveTaskRun);
        System.out.println(future.get());
    }
}
