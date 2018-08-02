package hang.thread.timer;

import com.hang.tools.time.TimeUtils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ZhangHang
 * @create 2018-05-14 16:29
 **/

/**
 * 1、Timer类可以延迟或者定时执行某个任务
 * 2、Timer类中每次只能调用一个线程去执行
 */
public class TimerRun {
    private static Timer timer=new Timer();

    public static void main(String[] args) {

        TimerTask task=getTask();
//        delayTimer(task,3000);
        timingTimer(task, TimeUtils.delaySecond(3));
    }

    private static TimerTask getTask(){
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                System.out.println("Thread Id:"+Thread.currentThread().getId());
            }
        };
        return timerTask;
    }

    /**
     * 延迟执行
     * @param timerTask
     * @param time
     */
    private static void delayTimer(TimerTask timerTask, long time){
        timer.schedule(timerTask,time);
    }

    /**
     * 定时执行
     * @param timerTask
     * @param time
     */
    private static void timingTimer(TimerTask timerTask,Date time){
        timer.schedule(timerTask,time);
    }
}
