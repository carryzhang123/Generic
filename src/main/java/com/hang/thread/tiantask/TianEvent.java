package com.hang.thread.tiantask;

/**
 * @author ZhangHang
 * @create 2018-05-02 21:33
 **/
public class TianEvent {
    private int threadId;
    private Runnable runnable;

    public static TianEvent valueOf(int threadId,Runnable runnable){
        TianEvent event=new TianEvent();
        event.setThreadId(threadId);
        event.setRunnable(runnable);
        return event;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }
}
